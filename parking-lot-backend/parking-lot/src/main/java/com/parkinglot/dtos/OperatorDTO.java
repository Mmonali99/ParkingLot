package com.parkinglot.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorDTO {
    private String empId;
    private String name;
}