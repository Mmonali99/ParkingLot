package com.parkinglot.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingFloorDTO {
    private Long id;
    private int floorNumber;
    private List<ParkingSpotDTO> spots;
}