package com.parkinglot.exceptions;

public class GateNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L; // Added

    public GateNotFoundException() {
        super("Gate not found in repository.");
    }
}