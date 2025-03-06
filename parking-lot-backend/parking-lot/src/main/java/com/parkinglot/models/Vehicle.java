package com.parkinglot.models;

import lombok.*;

import com.parkinglot.models.enums.VehicleType;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}