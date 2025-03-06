package com.parkinglot.services;

import com.parkinglot.dtos.VehicleDTO;
import com.parkinglot.models.Vehicle;
import com.parkinglot.repositories.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VehicleService {
    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    public VehicleDTO registerVehicle(VehicleDTO vehicleDTO) {
        logger.info("Registering vehicle with number: {}", vehicleDTO.getVehicleNumber());
        Vehicle vehicle = Vehicle.builder()
            .vehicleNumber(vehicleDTO.getVehicleNumber())
            .vehicleType(vehicleDTO.getVehicleType())
            .build();
        vehicle = vehicleRepository.save(vehicle);
        logger.info("Vehicle registered successfully: {}", vehicleDTO.getVehicleNumber());
        return VehicleDTO.builder()
            .vehicleNumber(vehicle.getVehicleNumber())
            .vehicleType(vehicle.getVehicleType())
            .build();
    }

    public VehicleDTO getVehicleByNumber(String vehicleNumber) {
        logger.info("Fetching vehicle with number: {}", vehicleNumber);
        Optional<Vehicle> vehicleOptional = vehicleRepository.findByVehicleNumber(vehicleNumber);
        if (!vehicleOptional.isPresent()) {
            logger.error("Vehicle not found with number: {}", vehicleNumber);
            throw new RuntimeException("Vehicle not found");
        }
        Vehicle vehicle = vehicleOptional.get();
        logger.info("Vehicle fetched successfully: {}", vehicleNumber);
        return VehicleDTO.builder()
            .vehicleNumber(vehicle.getVehicleNumber())
            .vehicleType(vehicle.getVehicleType())
            .build();
    }
}