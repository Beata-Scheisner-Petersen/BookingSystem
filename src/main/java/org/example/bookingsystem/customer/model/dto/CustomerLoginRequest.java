package org.example.bookingsystem.customer.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
public record CustomerLoginRequest(
        @Column(name = "email", unique = true)
        @NotBlank(message = "You must enter a email.")
        @Email(message = "Email format is invalid.")
        String email,

        @NotBlank(message = "You must enter a password.")
        String password
) {
}
