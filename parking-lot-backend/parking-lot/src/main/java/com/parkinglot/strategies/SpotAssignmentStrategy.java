package com.parkinglot.strategies;

import com.parkinglot.models.ParkingSpot;
import com.parkinglot.models.Vehicle;

public interface SpotAssignmentStrategy {
    ParkingSpot assignSpot(Vehicle vehicle);
}