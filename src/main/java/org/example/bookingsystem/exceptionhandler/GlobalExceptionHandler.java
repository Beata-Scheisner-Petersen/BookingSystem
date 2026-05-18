package org.example.bookingsystem.exceptionhandler;

import org.example.bookingsystem.customer.model.dto.*;
import org.example.bookingsystem.exceptionhandler.customexeptions.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

//    @ExceptionHandler(UsernameAlreadyExistsException.class)
//    public ResponseEntity<String> handleUsernameExists(UsernameAlreadyExistsException e) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//    }
}
