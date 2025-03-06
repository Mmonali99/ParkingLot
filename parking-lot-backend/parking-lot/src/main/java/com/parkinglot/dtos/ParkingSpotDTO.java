package com.parkinglot.dtos;

import com.parkinglot.models.enums.ParkingSpotStatus;
import com.parkinglot.models.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotDTO {
    private Long id;
    private int slotNumber;
    private ParkingSpotStatus status;
    private VehicleType vehicleType;
}