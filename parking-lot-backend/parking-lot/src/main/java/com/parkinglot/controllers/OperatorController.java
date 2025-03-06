package com.parkinglot.controllers;

import com.parkinglot.dtos.OperatorDTO;
import com.parkinglot.services.OperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operators")
public class OperatorController {
    private static final Logger logger = LoggerFactory.getLogger(OperatorController.class);

    @Autowired
    private OperatorService operatorService;

    @GetMapping
    public ResponseEntity<List<OperatorDTO>> getAllOperators() {
        logger.info("Fetching all operators");
        try {
            List<OperatorDTO> operators = operatorService.getAllOperators();
            logger.info("Fetched {} operators", operators.size());
            return ResponseEntity.ok(operators);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch operators - {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{empId}")
    public ResponseEntity<OperatorDTO> getOperatorById(@PathVariable String empId) {
        logger.info("Fetching operator with empId: {}", empId);
        try {
            OperatorDTO operator = operatorService.getOperatorById(empId);
            logger.info("Operator fetched successfully: {}", empId);
            return ResponseEntity.ok(operator);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch operator with empId: {} - {}", empId, e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<OperatorDTO> addOperator(@RequestBody OperatorDTO operatorDTO) {
        logger.info("Adding operator with empId: {}", operatorDTO.getEmpId());
        try {
            OperatorDTO addedOperator = operatorService.addOperator(operatorDTO);
            logger.info("Operator added successfully: {}", operatorDTO.getEmpId());
            return ResponseEntity.ok(addedOperator);
        } catch (RuntimeException e) {
            logger.error("Failed to add operator with empId: {} - {}", operatorDTO.getEmpId(), e.getMessage());
            throw e;
        }
    }
}