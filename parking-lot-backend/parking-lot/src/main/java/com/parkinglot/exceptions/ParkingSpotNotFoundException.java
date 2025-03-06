package com.parkinglot.exceptions;

public class ParkingSpotNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L; // Added

    public ParkingSpotNotFoundException() {
        super("Parking spot not found.");
    }
}