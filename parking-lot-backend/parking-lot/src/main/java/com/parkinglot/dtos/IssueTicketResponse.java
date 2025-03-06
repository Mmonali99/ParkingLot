package com.parkinglot.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueTicketResponse {
    private String ticketNumber;
    private Date entryTime;
    private String gateName;
    private int assignedSpotNumber;
}