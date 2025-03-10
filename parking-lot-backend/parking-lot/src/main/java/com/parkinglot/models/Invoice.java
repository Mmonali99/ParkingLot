package com.parkinglot.models;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

import com.parkinglot.models.enums.PaymentType;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private Date exitTime;
    private double totalCost;

    @OneToOne
    private Ticket ticket;

    @ManyToOne
    private Gate generatedAt;

    @ManyToOne
    private Operator generatedBy;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}