package org.example.bookingsystem.reservation.model;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CreateReservationRequest {

    private Long customerId;

    @NotNull (message = "Customer Id is required")
    private Long roomId;

    @NotNull(message = "Please specify whether an extra bed is needed" )
    private Boolean extraBed;

    @NotNull (message = "Check-in date is required")
    @FutureOrPresent(message = "Date has to be in the future")
    private LocalDate checkIn;

    @NotNull  (message = "Check-out date is required")
    @Future (message = "Date has to be in the future")
    private LocalDate checkOut;

    private int guests;

    public CreateReservationRequest() {
    }
    public Long getCustomerId() {
        return customerId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Boolean getExtraBed() {
        return extraBed;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }
}
