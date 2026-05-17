package org.example.bookingsystem.exceptionhandler.customexeptions;

public class CustomerExistException extends RuntimeException{
    public CustomerExistException(String message) {
        super(message);
    }
}
