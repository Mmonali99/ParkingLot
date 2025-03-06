package com.parkinglot.models;

import lombok.*;

import com.parkinglot.models.enums.ParkingSpotStatus;
import com.parkinglot.models.enums.VehicleType;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int slotNumber;

    @Enumerated(EnumType.STRING)
    private ParkingSpotStatus status;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @ManyToOne
    private ParkingFloor parkingFloor;

    @OneToOne
    private Vehicle vehicle;
}