package com.parkinglot.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password; // Added for registration
    private String email;
    private String role;
}