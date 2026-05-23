package org.example.bookingsystem.exceptionhandler.customexeptions;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String message) {
        super(message);
    }
}
