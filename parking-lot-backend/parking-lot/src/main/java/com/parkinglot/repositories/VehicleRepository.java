package com.parkinglot.repositories;

import com.parkinglot.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Optional<Vehicle> findByVehicleNumber(String vehicleNumber);
}