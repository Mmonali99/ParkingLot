package com.parkinglot.models;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operator {
    @Id
    private String empId;

    private String name;
}