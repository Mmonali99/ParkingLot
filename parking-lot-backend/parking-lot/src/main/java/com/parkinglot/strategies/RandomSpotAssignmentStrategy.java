package com.parkinglot.strategies;

import com.parkinglot.models.*;
import com.parkinglot.models.enums.ParkingSpotStatus;
import com.parkinglot.repositories.ParkingLotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Primary
public class RandomSpotAssignmentStrategy implements SpotAssignmentStrategy {
    private static final Logger logger = LoggerFactory.getLogger(RandomSpotAssignmentStrategy.class);

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingSpot assignSpot(Vehicle vehicle) {
        logger.info("Assigning spot for vehicle: {}", vehicle.getVehicleNumber());
        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findById(1L);
        if (!parkingLotOptional.isPresent()) {
            logger.error("Parking lot not found with ID: 1");
            throw new RuntimeException("Parking lot not found");
        }
        ParkingLot parkingLot = parkingLotOptional.get();

        List<ParkingFloor> floors = parkingLot.getParkingFloorList();
        for (ParkingFloor floor : floors) {
            List<ParkingSpot> spots = floor.getParkingSpotsList();
            for (ParkingSpot spot : spots) {
                if (spot.getVehicleType() == vehicle.getVehicleType() && 
                    spot.getStatus() == ParkingSpotStatus.AVAILABLE) {
                    logger.info("Assigned spot {} for vehicle: {}", spot.getSlotNumber(), vehicle.getVehicleNumber());
                    return spot;
                }
            }
        }
        logger.warn("No available spot found for vehicle: {}", vehicle.getVehicleNumber());
        return null;
    }
}