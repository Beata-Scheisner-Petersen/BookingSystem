package org.example.bookingsystem.exceptionhandler.customexeptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
