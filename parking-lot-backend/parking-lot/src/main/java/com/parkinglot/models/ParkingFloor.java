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
public class ParkingFloor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int floorNumber;

    @OneToMany(mappedBy = "parkingFloor")
    private List<ParkingSpot> parkingSpotsList;

    @ManyToOne
    private ParkingLot parkingLot;
}