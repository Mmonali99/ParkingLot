package com.parkinglot.dtos;

import com.parkinglot.models.enums.GateType ;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GateDTO {
    private Long id;
    private int gateNumber;
    private GateType gateType;
    private String operatorName;
}