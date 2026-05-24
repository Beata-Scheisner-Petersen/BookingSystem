package org.example.bookingsystem.customer.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerLoginRequest(
        @NotBlank(message = "You must enter a email.")
        @Email(message = "Email format is invalid.")
        String email,

        @NotBlank(message = "You must enter a password.")
        String password) {
}
