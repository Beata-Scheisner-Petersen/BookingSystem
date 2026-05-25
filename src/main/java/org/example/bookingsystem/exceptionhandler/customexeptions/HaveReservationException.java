package org.example.bookingsystem.exceptionhandler.customexeptions;

public class HaveReservationException extends RuntimeException {
    public HaveReservationException(String message){
        super(message);
    }
}
