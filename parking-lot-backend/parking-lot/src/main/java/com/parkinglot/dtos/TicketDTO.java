package com.parkinglot.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private String ticketNumber;
    private String vehicleNumber;
    private int slotNumber;
    private Date entryTime;
}