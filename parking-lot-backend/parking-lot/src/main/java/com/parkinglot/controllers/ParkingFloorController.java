package com.parkinglot.controllers;

import com.parkinglot.dtos.ParkingFloorDTO;
import com.parkinglot.services.ParkingFloorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-floors")
public class ParkingFloorController {
    private static final Logger logger = LoggerFactory.getLogger(ParkingFloorController.class);

    @Autowired
    private ParkingFloorService parkingFloorService;

    @GetMapping
    public ResponseEntity<List<ParkingFloorDTO>> getAllFloors() {
        logger.info("Fetching all parking floors");
        try {
            List<ParkingFloorDTO> floors = parkingFloorService.getAllFloors();
            logger.info("Fetched {} parking floors", floors.size());
            return ResponseEntity.ok(floors);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch parking floors - {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{floorId}")
    public ResponseEntity<ParkingFloorDTO> getFloorById(@PathVariable Long floorId) {
        logger.info("Fetching parking floor with ID: {}", floorId);
        try {
            ParkingFloorDTO floor = parkingFloorService.getFloorById(floorId);
            logger.info("Parking floor fetched successfully: {}", floorId);
            return ResponseEntity.ok(floor);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch parking floor with ID: {} - {}", floorId, e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<ParkingFloorDTO> addFloor(@RequestBody ParkingFloorDTO floorDTO) {
        logger.info("Adding parking floor with number: {}", floorDTO.getFloorNumber());
        try {
            ParkingFloorDTO addedFloor = parkingFloorService.addFloor(floorDTO);
            logger.info("Parking floor added successfully: {}", addedFloor.getId());
            return ResponseEntity.ok(addedFloor);
        } catch (RuntimeException e) {
            logger.error("Failed to add parking floor with number: {} - {}", floorDTO.getFloorNumber(), e.getMessage());
            throw e;
        }
    }
}