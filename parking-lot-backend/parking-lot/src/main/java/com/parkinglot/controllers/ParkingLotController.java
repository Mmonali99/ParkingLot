package com.parkinglot.controllers;

import com.parkinglot.dtos.ParkingLotDTO;
import com.parkinglot.services.ParkingLotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {
    private static final Logger logger = LoggerFactory.getLogger(ParkingLotController.class);

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping
    public ResponseEntity<List<ParkingLotDTO>> getAllParkingLots() {
        logger.info("Fetching all parking lots");
        try {
            List<ParkingLotDTO> lots = parkingLotService.getAllParkingLots();
            logger.info("Fetched {} parking lots", lots.size());
            return ResponseEntity.ok(lots);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch parking lots - {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{lotId}")
    public ResponseEntity<ParkingLotDTO> getParkingLotById(@PathVariable Long lotId) {
        logger.info("Fetching parking lot with ID: {}", lotId);
        try {
            ParkingLotDTO lot = parkingLotService.getParkingLotById(lotId);
            logger.info("Parking lot fetched successfully: {}", lotId);
            return ResponseEntity.ok(lot);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch parking lot with ID: {} - {}", lotId, e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<ParkingLotDTO> addParkingLot(@RequestBody ParkingLotDTO parkingLotDTO) {
        logger.info("Adding parking lot with name: {}", parkingLotDTO.getName());
        try {
            ParkingLotDTO addedLot = parkingLotService.addParkingLot(parkingLotDTO);
            logger.info("Parking lot added successfully: {}", addedLot.getId());
            return ResponseEntity.ok(addedLot);
        } catch (RuntimeException e) {
            logger.error("Failed to add parking lot with name: {} - {}", parkingLotDTO.getName(), e.getMessage());
            throw e;
        }
    }
}