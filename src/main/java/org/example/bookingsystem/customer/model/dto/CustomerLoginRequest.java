package org.example.bookingsystem.customer.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerLoginRequest(
        @NotBlank(message = "You must enter a email.")
        @Email(message = "Email format is invalid.")
        String email,

        @NotBlank(message = "You must enter a password.")
        @Size(min = 10, message = "password needs to be at least 10 character long")
        @Pattern(regexp = ".*[^a-zA-Z0-9].*", message = "Password have to contain a least one special character")
        @Pattern(regexp = ".*[A-Z].*", message = "Password have to contain a least one uppercase letter")
        @Pattern(regexp = ".*[a-z].*", message = "Password have to contain a least one lowercase letter")
        @Pattern(regexp = ".*[0-9].*", message = "Password have to contain a least one number")
        String password) {
}
