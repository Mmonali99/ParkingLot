package com.parkinglot.models;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "parkingLot")
    private List<ParkingFloor> parkingFloorList;

    @OneToMany
    private List<Gate> entryGates;

    @OneToMany
    private List<Gate> exitGates;
}