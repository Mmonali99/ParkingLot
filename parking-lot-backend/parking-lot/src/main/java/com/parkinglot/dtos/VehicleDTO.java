package com.parkinglot.dtos;

import com.parkinglot.models.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private String vehicleNumber;
    private VehicleType vehicleType;
}