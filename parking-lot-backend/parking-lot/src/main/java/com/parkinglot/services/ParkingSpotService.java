package com.parkinglot.services;

import com.parkinglot.dtos.ParkingSpotDTO;
import com.parkinglot.exceptions.ParkingSpotNotFoundException;
import com.parkinglot.models.ParkingSpot;
import com.parkinglot.repositories.ParkingSpotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingSpotService {
    private static final Logger logger = LoggerFactory.getLogger(ParkingSpotService.class);

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public List<ParkingSpotDTO> getAllSpots() {
        logger.info("Fetching all parking spots");
        List<ParkingSpot> spots = parkingSpotRepository.findAll();
        List<ParkingSpotDTO> spotDTOs = spots.stream().map(this::toDTO).collect(Collectors.toList());
        logger.info("Fetched {} parking spots", spotDTOs.size());
        return spotDTOs;
    }

    public ParkingSpotDTO getSpotById(Long spotId) {
        logger.info("Fetching parking spot with ID: {}", spotId);
        Optional<ParkingSpot> spotOptional = parkingSpotRepository.findById(spotId);
        if (!spotOptional.isPresent()) {
            logger.error("Parking spot not found with ID: {}", spotId);
            throw new ParkingSpotNotFoundException();
        }
        ParkingSpot spot = spotOptional.get();
        logger.info("Parking spot fetched successfully: {}", spotId);
        return toDTO(spot);
    }

    @Transactional
    public ParkingSpotDTO updateSpot(ParkingSpotDTO spotDTO) {
        logger.info("Updating parking spot with ID: {}", spotDTO.getId());
        Optional<ParkingSpot> spotOptional = parkingSpotRepository.findById(spotDTO.getId());
        if (!spotOptional.isPresent()) {
            logger.error("Parking spot not found with ID: {}", spotDTO.getId());
            throw new ParkingSpotNotFoundException();
        }
        ParkingSpot spot = spotOptional.get();
        spot.setSlotNumber(spotDTO.getSlotNumber());
        spot.setStatus(spotDTO.getStatus());
        spot.setVehicleType(spotDTO.getVehicleType());
        spot = parkingSpotRepository.save(spot);
        logger.info("Parking spot updated successfully: {}", spotDTO.getId());
        return toDTO(spot);
    }

    private ParkingSpotDTO toDTO(ParkingSpot spot) {
        return ParkingSpotDTO.builder()
            .id(spot.getId())
            .slotNumber(spot.getSlotNumber())
            .status(spot.getStatus())
            .vehicleType(spot.getVehicleType())
            .build();
    }
}