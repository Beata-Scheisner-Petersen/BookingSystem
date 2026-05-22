package org.example.bookingsystem.reservation.utils;

import org.example.bookingsystem.exceptionhandler.customexeptions.BadRequestException;

import java.time.LocalDate;

public class Validations {

    public static void validateDateRange(LocalDate checkIn, LocalDate checkOut){
        if (checkIn.isAfter(checkOut)) {
            throw new BadRequestException("CheckIn date should be before check-out date.");
        }

    }

}
