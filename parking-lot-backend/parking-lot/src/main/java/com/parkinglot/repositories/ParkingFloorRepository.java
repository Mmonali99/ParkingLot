package com.parkinglot.repositories;

import com.parkinglot.models.ParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingFloorRepository extends JpaRepository<ParkingFloor, Long> {
    Optional<ParkingFloor> findByFloorNumber(int floorNumber);
}