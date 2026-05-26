package org.example.bookingsystem.customer.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerLoginRequest(
        @NotBlank(message = "You must enter an email.")
        @Email(message = "Not a valid email.")
        String email,

        @NotBlank(message = "You must enter a password.")

        String password) {
}
