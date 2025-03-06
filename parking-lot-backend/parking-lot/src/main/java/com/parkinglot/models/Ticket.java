package com.parkinglot.models;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    private String number;

    private Date entryTime;

    @OneToOne
    private Vehicle vehicle;

    @OneToOne
    private ParkingSpot parkingSpot;

    @ManyToOne
    private Gate generatedAt;

    @ManyToOne
    private Operator generatedBy;
}