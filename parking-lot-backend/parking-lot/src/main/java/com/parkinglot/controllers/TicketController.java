package com.parkinglot.controllers;

import com.parkinglot.dtos.IssueTicketRequest;
import com.parkinglot.dtos.IssueTicketResponse;
import com.parkinglot.dtos.TicketDTO;
import com.parkinglot.models.Invoice;
import com.parkinglot.services.TicketService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        logger.info("Fetching all tickets");
        try {
            List<TicketDTO> tickets = ticketService.getAllTickets();
            logger.info("Fetched {} tickets", tickets.size());
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            logger.error("Failed to fetch all tickets: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/issue")
    public ResponseEntity<IssueTicketResponse> issueTicket(@RequestBody IssueTicketRequest request) {
        logger.info("Issuing ticket for vehicle: {}", request.getVehicleNumber());
        try {
            IssueTicketResponse response = ticketService.issueTicket(request);
            logger.info("Ticket issued successfully: {}", response.getTicketNumber());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Failed to issue ticket for vehicle: {} - {}", request.getVehicleNumber(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{ticketNumber}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable String ticketNumber) {
        logger.info("Fetching ticket with number: {}", ticketNumber);
        try {
            TicketDTO ticket = ticketService.getTicketById(ticketNumber);
            logger.info("Ticket fetched successfully: {}", ticketNumber);
            return ResponseEntity.ok(ticket);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch ticket with number: {} - {}", ticketNumber, e.getMessage());
            throw e;
        }
    }

    @PostMapping("/exit/{ticketNumber}")
    public ResponseEntity<Invoice> exitVehicle(@PathVariable String ticketNumber) {
        logger.info("Processing exit for ticket: {}", ticketNumber);
        try {
            Invoice invoice = ticketService.exitVehicle(ticketNumber);
            logger.info("Vehicle exit processed, invoice generated: {}", invoice.getNumber());
            return ResponseEntity.ok(invoice);
        } catch (RuntimeException e) {
            logger.error("Failed to process exit for ticket: {} - {}", ticketNumber, e.getMessage());
            throw e;
        }
    }
}