package com.parkinglot.repositories;

import com.parkinglot.models.ParkingSpot;
import com.parkinglot.models.enums.ParkingSpotStatus;
import com.parkinglot.models.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    Optional<ParkingSpot> findBySlotNumber(int slotNumber);
    List<ParkingSpot> findByParkingFloorIdAndVehicleTypeAndStatus(Long parkingFloorId, VehicleType vehicleType, ParkingSpotStatus status);
}