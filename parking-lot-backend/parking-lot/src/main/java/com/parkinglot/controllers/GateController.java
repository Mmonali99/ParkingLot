package com.parkinglot.controllers;

import com.parkinglot.dtos.GateDTO;
import com.parkinglot.services.GateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gates")
public class GateController {
    private static final Logger logger = LoggerFactory.getLogger(GateController.class);

    @Autowired
    private GateService gateService;

    @GetMapping("/{gateId}")
    public ResponseEntity<GateDTO> getGateById(@PathVariable Long gateId) {
        logger.info("Fetching gate with ID: {}", gateId);
        try {
            GateDTO gateDTO = gateService.getGateById(gateId);
            logger.info("Gate fetched successfully: {}", gateId);
            return ResponseEntity.ok(gateDTO);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch gate with ID: {} - {}", gateId, e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<GateDTO>> getAllGates() {
        logger.info("Fetching all gates");
        try {
            List<GateDTO> gates = gateService.getAllGates();
            logger.info("Fetched {} gates", gates.size());
            return ResponseEntity.ok(gates);
        } catch (RuntimeException e) {
            logger.error("Failed to fetch gates - {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{gateId}")
    public ResponseEntity<GateDTO> updateGate(@PathVariable Long gateId, @RequestBody GateDTO gateDTO) {
        logger.info("Updating gate with ID: {}", gateId);
        try {
            gateDTO.setId(gateId);
            GateDTO updatedGate = gateService.updateGate(gateDTO);
            logger.info("Gate updated successfully: {}", gateId);
            return ResponseEntity.ok(updatedGate);
        } catch (RuntimeException e) {
            logger.error("Failed to update gate with ID: {} - {}", gateId, e.getMessage());
            throw e;
        }
    }
}