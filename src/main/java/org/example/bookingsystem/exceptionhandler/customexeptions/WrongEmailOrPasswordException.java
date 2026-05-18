package org.example.bookingsystem.exceptionhandler.customexeptions;

public class WrongEmailOrPasswordException extends RuntimeException{
    public WrongEmailOrPasswordException(String message) {
        super(message);
    }
}
