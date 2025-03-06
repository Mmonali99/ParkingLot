package com.parkinglot.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotDTO {
    private Long id;
    private String name;
    private String address;
    private List<ParkingFloorDTO> floors;
}