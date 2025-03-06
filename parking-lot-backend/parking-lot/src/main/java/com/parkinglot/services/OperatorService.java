package com.parkinglot.services;

import com.parkinglot.dtos.OperatorDTO;
import com.parkinglot.models.Operator;
import com.parkinglot.repositories.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;

    public List<OperatorDTO> getAllOperators() {
        List<Operator> operators = operatorRepository.findAll();
        List<OperatorDTO> operatorDTOs = new ArrayList<OperatorDTO>();
        for (Operator operator : operators) {
            operatorDTOs.add(toDTO(operator));
        }
        return operatorDTOs;
    }

    public OperatorDTO getOperatorById(String empId) {
        Optional<Operator> operatorOptional = operatorRepository.findByEmpId(empId);
        if (!operatorOptional.isPresent()) {
            throw new RuntimeException("Operator not found");
        }
        return toDTO(operatorOptional.get());
    }

    public OperatorDTO addOperator(OperatorDTO operatorDTO) {
        Operator operator = new Operator();
        operator.setEmpId(operatorDTO.getEmpId());
        operator.setName(operatorDTO.getName());
        operator = operatorRepository.save(operator);
        return toDTO(operator);
    }

    private OperatorDTO toDTO(Operator operator) {
        OperatorDTO dto = new OperatorDTO();
        dto.setEmpId(operator.getEmpId());
        dto.setName(operator.getName());
        return dto;
    }
}