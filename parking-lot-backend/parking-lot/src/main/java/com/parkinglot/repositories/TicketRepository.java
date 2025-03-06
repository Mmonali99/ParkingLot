package com.parkinglot.repositories;

import com.parkinglot.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    Optional<Ticket> findByNumber(String ticketNumber);
}