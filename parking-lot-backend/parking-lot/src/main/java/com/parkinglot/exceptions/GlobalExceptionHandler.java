package com.parkinglot.exceptions;

import com.parkinglot.dtos.IssueTicketResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GateNotFoundException.class)
    public ResponseEntity<IssueTicketResponse> handleGateNotFoundException(GateNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(IssueTicketResponse.builder()
                        .ticketNumber(null)
                        .entryTime(null)
                        .gateName(null)
                        .assignedSpotNumber(0)
                        .build());
    }

    @ExceptionHandler(ParkingSpotNotFoundException.class)
    public ResponseEntity<IssueTicketResponse> handleParkingSpotNotFoundException(ParkingSpotNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(IssueTicketResponse.builder()
                        .ticketNumber(null)
                        .entryTime(null)
                        .gateName(null)
                        .assignedSpotNumber(0)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + ex.getMessage());
    }
}