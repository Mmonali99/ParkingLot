package com.parkinglot.services;

import com.parkinglot.dtos.ParkingFloorDTO;
import com.parkinglot.dtos.ParkingLotDTO;
import com.parkinglot.models.ParkingFloor;
import com.parkinglot.models.ParkingLot;
import com.parkinglot.repositories.ParkingLotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {
    private static final Logger logger = LoggerFactory.getLogger(ParkingLotService.class);

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingFloorService parkingFloorService;

    public List<ParkingLotDTO> getAllParkingLots() {
        logger.info("Fetching all parking lots");
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        List<ParkingLotDTO> parkingLotDTOs = parkingLots.stream().map(this::toDTO).collect(Collectors.toList());
        logger.info("Fetched {} parking lots", parkingLotDTOs.size());
        return parkingLotDTOs;
    }

    public ParkingLotDTO getParkingLotById(Long lotId) {
        logger.info("Fetching parking lot with ID: {}", lotId);
        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findById(lotId);
        if (!parkingLotOptional.isPresent()) {
            logger.error("Parking lot not found with ID: {}", lotId);
            throw new RuntimeException("Parking lot not found");
        }
        ParkingLot parkingLot = parkingLotOptional.get();
        logger.info("Parking lot fetched successfully: {}", lotId);
        return toDTO(parkingLot);
    }

    @Transactional
    public ParkingLotDTO addParkingLot(ParkingLotDTO parkingLotDTO) {
        logger.info("Adding parking lot with name: {}", parkingLotDTO.getName());
        ParkingLot parkingLot = ParkingLot.builder()
            .name(parkingLotDTO.getName())
            .address(parkingLotDTO.getAddress())
            .build();
        parkingLot = parkingLotRepository.save(parkingLot);
        logger.info("Parking lot added successfully: {}", parkingLot.getId());
        return toDTO(parkingLot);
    }

    private ParkingLotDTO toDTO(ParkingLot parkingLot) {
        List<ParkingFloorDTO> floors = null;
        if (parkingLot.getParkingFloorList() != null) {
            floors = parkingLot.getParkingFloorList().stream()
                .map(floor -> parkingFloorService.getFloorById(floor.getId()))
                .collect(Collectors.toList());
        }
        return ParkingLotDTO.builder()
            .id(parkingLot.getId())
            .name(parkingLot.getName())
            .address(parkingLot.getAddress())
            .floors(floors)
            .build();
    }
}