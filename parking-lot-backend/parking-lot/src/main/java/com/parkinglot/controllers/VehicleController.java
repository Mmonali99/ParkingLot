package com.parkinglot.controllers;

import com.parkinglot.dtos.VehicleDTO;
import com.parkinglot.services.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDTO> registerVehicle(@RequestBody VehicleDTO vehicleDTO) {
        logger.info("Registering vehicle with number: {}", vehicleDTO.getVehicleNumber());
        try {
            VehicleDTO registeredVehicle = vehicleService.registerVehicle(vehicleDTO);
            logger.info("Vehicle registered successfully: {}", vehicleDTO.getVehicleNumber());
            return ResponseEntity.ok(registeredVehicle);
        } catch (RuntimeException e) {
            logger.error("Failed to register vehicle with number: {} - {}", vehicleDTO.getVehicleNumber(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{vehicleNumber}")
    public ResponseEntity<VehicleDTO> getVehicleByNumber(@PathVariable String vehicleNumber) {
        logger.info("Fetching vehicle with number: {}", vehicleNumber);
        try {
            VehicleDTO vehicle = vehicleService.getVehicleByNumber(vehicleNumber);
            logger.info("Vehicle fetched successfully: {}", vehicleNumber);
            return ResponseEntity.ok(vehicle);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch vehicle with number: {} - {}", vehicleNumber, e.getMessage());
            throw e;
        }
    }
}