package org.example.bookingsystem.reservation.utils;

import org.example.bookingsystem.error.BadRequestException;
import org.example.bookingsystem.reservation.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public class Validations {

    public static void validateDateRange(LocalDate checkIn, LocalDate checkOut){
        if (checkIn.isAfter(checkOut)) {
            throw new BadRequestException("CheckIn date should be before check-out date.");
        }

    }

}
