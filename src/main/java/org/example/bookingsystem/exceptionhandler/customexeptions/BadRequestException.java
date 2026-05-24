package org.example.bookingsystem.exceptionhandler.customexeptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
