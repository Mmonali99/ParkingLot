package com.parkinglot.repositories;

import com.parkinglot.models.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, String> {
    Optional<Operator> findByEmpId(String empId);
}