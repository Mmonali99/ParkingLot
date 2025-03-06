package com.parkinglot.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String jwtToken;
    private String role;
    
    
}