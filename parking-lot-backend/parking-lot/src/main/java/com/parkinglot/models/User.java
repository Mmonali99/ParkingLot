package com.parkinglot.models;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "users") // Rename table to "users" to avoid reserved keyword
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password; // Hashed
    private String email;
    private String role;
}