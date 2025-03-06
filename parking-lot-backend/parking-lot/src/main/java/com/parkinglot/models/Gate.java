package com.parkinglot.models;

import lombok.*;

import com.parkinglot.models.enums.GateType;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int gateNumber;

    @Enumerated(EnumType.STRING)
    private GateType gateType;

    @ManyToOne
    private Operator currentOperator;
}