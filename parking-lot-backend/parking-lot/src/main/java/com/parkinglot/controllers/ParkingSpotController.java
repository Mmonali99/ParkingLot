package com.parkinglot.controllers;

import com.parkinglot.dtos.ParkingSpotDTO;
import com.parkinglot.services.ParkingSpotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spots")
public class ParkingSpotController {
    private static final Logger logger = LoggerFactory.getLogger(ParkingSpotController.class);

    @Autowired
    private ParkingSpotService parkingSpotService;

    @GetMapping
    public ResponseEntity<List<ParkingSpotDTO>> getAllSpots() {
        logger.info("Fetching all parking spots");
        try {
            List<ParkingSpotDTO> spots = parkingSpotService.getAllSpots();
            logger.info("Fetched {} parking spots", spots.size());
            return ResponseEntity.ok(spots);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch parking spots - {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{spotId}")
    public ResponseEntity<ParkingSpotDTO> getSpotById(@PathVariable Long spotId) {
        logger.info("Fetching parking spot with ID: {}", spotId);
        try {
            ParkingSpotDTO spot = parkingSpotService.getSpotById(spotId);
            logger.info("Parking spot fetched successfully: {}", spotId);
            return ResponseEntity.ok(spot);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch parking spot with ID: {} - {}", spotId, e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{spotId}")
    public ResponseEntity<ParkingSpotDTO> updateSpot(@PathVariable Long spotId, @RequestBody ParkingSpotDTO spotDTO) {
        logger.info("Updating parking spot with ID: {}", spotId);
        try {
            spotDTO.setId(spotId);
            ParkingSpotDTO updatedSpot = parkingSpotService.updateSpot(spotDTO);
            logger.info("Parking spot updated successfully: {}", spotId);
            return ResponseEntity.ok(updatedSpot);
        } catch (RuntimeException e) {
            logger.error("Failed to update parking spot with ID: {} - {}", spotId, e.getMessage());
            throw e;
        }
    }
}