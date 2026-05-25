package org.example.bookingsystem.reservation.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CreateAvailabilityRequest {


    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Date has to be in the future")
    private LocalDate checkIn;

    @NotNull  (message = "Check-out date is required")
    @Future(message = "Date has to be in the future")
    private LocalDate checkOut;

    @Min(value = 1, message = "Guests must be at least 1")
    private int guests;

    @NotNull(message = "Please specify extra bed")
    private Boolean extraBed;

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public Boolean getExtraBed() {
        return extraBed;
    }

    public void setExtraBed(Boolean extraBed) {
        this.extraBed = extraBed;
    }
}
