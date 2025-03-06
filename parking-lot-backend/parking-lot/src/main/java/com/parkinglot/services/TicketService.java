package com.parkinglot.services;

import com.parkinglot.dtos.IssueTicketRequest;
import com.parkinglot.dtos.IssueTicketResponse;
import com.parkinglot.dtos.TicketDTO;
import com.parkinglot.exceptions.GateNotFoundException;
import com.parkinglot.exceptions.ParkingSpotNotFoundException;
import com.parkinglot.models.*;
import com.parkinglot.models.enums.ParkingSpotStatus;
import com.parkinglot.models.enums.PaymentType;
import com.parkinglot.repositories.*;
import com.parkinglot.strategies.PaymentStrategy;
import com.parkinglot.strategies.SpotAssignmentStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private SpotAssignmentStrategy spotAssignmentStrategy;

    @Autowired
    private PaymentStrategy paymentStrategy;

    @Transactional
    public IssueTicketResponse issueTicket(IssueTicketRequest request) {
        logger.info("Issuing ticket for vehicle: {}", request.getVehicleNumber());
        Optional<Gate> gateOptional = gateRepository.findById(request.getGateId());
        if (!gateOptional.isPresent()) {
            logger.error("Gate not found with ID: {}", request.getGateId());
            throw new GateNotFoundException();
        }
        Gate gate = gateOptional.get();

        Optional<Vehicle> vehicleOptional = vehicleRepository.findByVehicleNumber(request.getVehicleNumber());
        Vehicle vehicle;
        if (!vehicleOptional.isPresent()) {
            vehicle = Vehicle.builder()
                .vehicleNumber(request.getVehicleNumber())
                .vehicleType(request.getVehicleType())
                .build();
            vehicle = vehicleRepository.save(vehicle);
            logger.info("New vehicle registered: {}", request.getVehicleNumber());
        } else {
            vehicle = vehicleOptional.get();
        }

        ParkingSpot spot = spotAssignmentStrategy.assignSpot(vehicle);
        if (spot == null) {
            logger.error("No available parking spot for vehicle: {}", request.getVehicleNumber());
            throw new ParkingSpotNotFoundException();
        }

        spot.setStatus(ParkingSpotStatus.OCCUPIED);
        spot.setVehicle(vehicle);
        parkingSpotRepository.save(spot);

        Ticket ticket = Ticket.builder()
            .number(String.valueOf(System.currentTimeMillis()))
            .entryTime(new Date())
            .vehicle(vehicle)
            .parkingSpot(spot)
            .generatedAt(gate)
            .generatedBy(gate.getCurrentOperator())
            .build();
        ticket = ticketRepository.save(ticket);
        logger.info("Ticket issued successfully: {}", ticket.getNumber());

        return IssueTicketResponse.builder()
            .ticketNumber(ticket.getNumber())
            .entryTime(ticket.getEntryTime())
            .gateName(String.valueOf(gate.getGateNumber()))
            .assignedSpotNumber(spot.getSlotNumber())
            .build();
    }

    public TicketDTO getTicketById(String ticketNumber) {
        logger.info("Fetching ticket with number: {}", ticketNumber);
        Optional<Ticket> ticketOptional = ticketRepository.findByNumber(ticketNumber);
        if (!ticketOptional.isPresent()) {
            logger.error("Ticket not found with number: {}", ticketNumber);
            throw new RuntimeException("Ticket not found");
        }
        Ticket ticket = ticketOptional.get();
        logger.info("Ticket fetched successfully: {}", ticketNumber);
        return TicketDTO.builder()
            .ticketNumber(ticket.getNumber())
            .vehicleNumber(ticket.getVehicle().getVehicleNumber())
            .slotNumber(ticket.getParkingSpot().getSlotNumber())
            .entryTime(ticket.getEntryTime())
            .build();
    }
    

        public List<TicketDTO> getAllTickets() {
            logger.info("Retrieving all tickets from repository");
            try {
                List<Ticket> tickets = ticketRepository.findAll();
                List<TicketDTO> ticketDTOs = tickets.stream()
                    .map(ticket -> TicketDTO.builder()
                        .ticketNumber(ticket.getNumber())
                        .vehicleNumber(ticket.getVehicle().getVehicleNumber())
                        .slotNumber(ticket.getParkingSpot() != null ? ticket.getParkingSpot().getSlotNumber() : 0)
                        .entryTime(ticket.getEntryTime())
                        .build())
                    .collect(Collectors.toList());
                logger.info("Retrieved {} tickets", ticketDTOs.size());
                return ticketDTOs;
            } catch (Exception e) {
                logger.error("Error retrieving tickets: {}", e.getMessage(), e);
                throw e;
            }
        }

    @Transactional
    public Invoice exitVehicle(String ticketNumber) {
        logger.info("Processing exit for ticket: {}", ticketNumber);
        Optional<Ticket> ticketOptional = ticketRepository.findByNumber(ticketNumber);
        if (!ticketOptional.isPresent()) {
            logger.error("Ticket not found with number: {}", ticketNumber);
            throw new RuntimeException("Ticket not found");
        }
        Ticket ticket = ticketOptional.get();

        ParkingSpot spot = ticket.getParkingSpot();
        spot.setStatus(ParkingSpotStatus.AVAILABLE);
        spot.setVehicle(null);
        parkingSpotRepository.save(spot);

        long timeDiff = new Date().getTime() - ticket.getEntryTime().getTime();
        double hours = TimeUnit.MILLISECONDS.toHours(timeDiff);
        double ratePerHour = 10.0;
        double totalCost = hours * ratePerHour;

        Invoice invoice = Invoice.builder()
            .number("INV-" + ticketNumber)
            .exitTime(new Date())
            .totalCost(totalCost)
            .ticket(ticket)
            .generatedAt(ticket.getGeneratedAt())
            .generatedBy(ticket.getGeneratedBy())
            .paymentType(PaymentType.CASH)
            .build();

        paymentStrategy.processPayment(invoice, totalCost);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        logger.info("Vehicle exit processed, invoice generated: {}", savedInvoice.getNumber());
        return savedInvoice;
    }
}