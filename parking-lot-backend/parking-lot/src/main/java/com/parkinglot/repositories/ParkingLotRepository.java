package com.parkinglot.repositories;

import com.parkinglot.models.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    Optional<ParkingLot> findByName(String name);
}