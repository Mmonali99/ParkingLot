package com.parkinglot.repositories;

import com.parkinglot.models.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GateRepository extends JpaRepository<Gate, Long> {
    Optional<Gate> findByGateNumber(int gateNumber);
}