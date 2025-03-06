package com.parkinglot.services;

import com.parkinglot.dtos.GateDTO;
import com.parkinglot.exceptions.GateNotFoundException;
import com.parkinglot.models.Gate;
import com.parkinglot.repositories.GateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GateService {
    private static final Logger logger = LoggerFactory.getLogger(GateService.class);

    @Autowired
    private GateRepository gateRepository;

    public GateDTO getGateById(Long gateId) {
        logger.info("Fetching gate with ID: {}", gateId);
        Optional<Gate> gateOptional = gateRepository.findById(gateId);
        if (!gateOptional.isPresent()) {
            logger.error("Gate not found with ID: {}", gateId);
            throw new GateNotFoundException();
        }
        Gate gate = gateOptional.get();
        logger.info("Gate fetched successfully: {}", gateId);
        return toDTO(gate);
    }

    public List<GateDTO> getAllGates() {
        logger.info("Fetching all gates");
        List<Gate> gates = gateRepository.findAll();
        List<GateDTO> gateDTOs = gates.stream().map(this::toDTO).collect(Collectors.toList());
        logger.info("Fetched {} gates", gateDTOs.size());
        return gateDTOs;
    }

    @Transactional
    public GateDTO updateGate(GateDTO gateDTO) {
        logger.info("Updating gate with ID: {}", gateDTO.getId());
        Optional<Gate> gateOptional = gateRepository.findById(gateDTO.getId());
        if (!gateOptional.isPresent()) {
            logger.error("Gate not found with ID: {}", gateDTO.getId());
            throw new GateNotFoundException();
        }
        Gate gate = gateOptional.get();
        gate.setGateNumber(gateDTO.getGateNumber());
        gate.setGateType(gateDTO.getGateType());
        gate = gateRepository.save(gate);
        logger.info("Gate updated successfully: {}", gateDTO.getId());
        return toDTO(gate);
    }

    private GateDTO toDTO(Gate gate) {
        return GateDTO.builder()
            .id(gate.getId())
            .gateNumber(gate.getGateNumber())
            .gateType(gate.getGateType())
            .operatorName(gate.getCurrentOperator() != null ? gate.getCurrentOperator().getName() : null)
            .build();
    }
}