package com.parkinglot.services;

import com.parkinglot.dtos.ParkingFloorDTO;
import com.parkinglot.dtos.ParkingSpotDTO;
import com.parkinglot.models.ParkingFloor;
import com.parkinglot.models.ParkingSpot;
import com.parkinglot.repositories.ParkingFloorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingFloorService {
    private static final Logger logger = LoggerFactory.getLogger(ParkingFloorService.class);

    @Autowired
    private ParkingFloorRepository parkingFloorRepository;

    public List<ParkingFloorDTO> getAllFloors() {
        logger.info("Fetching all parking floors");
        List<ParkingFloor> floors = parkingFloorRepository.findAll();
        List<ParkingFloorDTO> floorDTOs = floors.stream().map(this::toDTO).collect(Collectors.toList());
        logger.info("Fetched {} parking floors", floorDTOs.size());
        return floorDTOs;
    }

    public ParkingFloorDTO getFloorById(Long floorId) {
        logger.info("Fetching parking floor with ID: {}", floorId);
        Optional<ParkingFloor> floorOptional = parkingFloorRepository.findById(floorId);
        if (!floorOptional.isPresent()) {
            logger.error("Parking floor not found with ID: {}", floorId);
            throw new RuntimeException("Parking floor not found");
        }
        ParkingFloor floor = floorOptional.get();
        logger.info("Parking floor fetched successfully: {}", floorId);
        return toDTO(floor);
    }

    @Transactional
    public ParkingFloorDTO addFloor(ParkingFloorDTO floorDTO) {
        logger.info("Adding parking floor with number: {}", floorDTO.getFloorNumber());
        ParkingFloor floor = ParkingFloor.builder()
            .floorNumber(floorDTO.getFloorNumber())
            .build();
        floor = parkingFloorRepository.save(floor);
        logger.info("Parking floor added successfully: {}", floor.getId());
        return toDTO(floor);
    }

    private ParkingFloorDTO toDTO(ParkingFloor floor) {
        List<ParkingSpotDTO> spots = null;
        if (floor.getParkingSpotsList() != null) {
            spots = floor.getParkingSpotsList().stream()
                .map(spot -> ParkingSpotDTO.builder()
                    .id(spot.getId())
                    .slotNumber(spot.getSlotNumber())
                    .status(spot.getStatus())
                    .vehicleType(spot.getVehicleType())
                    .build())
                .collect(Collectors.toList());
        }
        return ParkingFloorDTO.builder()
            .id(floor.getId())
            .floorNumber(floor.getFloorNumber())
            .spots(spots)
            .build();
    }
}