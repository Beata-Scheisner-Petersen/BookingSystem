package org.example.bookingsystem.reservation.model;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CreateReservationRequest {

    @NotNull (message = "Customer Id is required")
    private Long customerId;

    @NotNull (message = "Customer Id is required")
    private Long roomId;

    @NotNull (message = "Reservation status is required")
    private Boolean isReserved;

    @NotNull(message = "Please specify whether an extra bed is needed" )
    private Boolean extraBed;

    @NotNull (message = "Check-in date is required")
    @FutureOrPresent(message = "Date has to be in the future")
    private LocalDate checkIn;

    @NotNull  (message = "Check-out date is required")
    @Future (message = "Date has to be in the future")
    private LocalDate checkOut;

    @NotNull (message = "Total Cost is required")
    private Double totalCost;

    public CreateReservationRequest() {
    }
}
