package org.example.bookingsystem.reservation.model.dto;

import org.example.bookingsystem.reservation.model.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetAllCustomerReservationsDto(
        Long id,
        LocalDate checkIn,
        LocalDate checkOut,
        int roomNumber,
        BigDecimal totalCost,
        ReservationStatus status
) {}