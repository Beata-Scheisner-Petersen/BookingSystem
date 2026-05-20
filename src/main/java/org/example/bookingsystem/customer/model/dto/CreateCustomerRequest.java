package org.example.bookingsystem.customer.model.dto;

import jakarta.validation.constraints.*;

public record CreateCustomerRequest(
        @NotBlank(message = "You must enter a firstname.")
        String firstname,

        @NotBlank(message = "You must enter a lastname.")
        String lastname,

        @NotBlank(message = "You must enter an identification number.")
        @Pattern(regexp = "^(\\d{6}|\\d{8})-\\d{4}$", message = "invalid format of identification number.")
        String identificationNumber,

        @NotBlank(message = "You must enter a email.")
        @Email(message = "Email format is invalid.")
        String email,

        @NotBlank(message = "You must enter a password.")
        @Pattern(regexp = ".*[^a-zA-Z0-9].*", message = "Password have to contain a least one special character")
        @Pattern(regexp = ".*[A-Z].*", message = "Password have to contain a least one uppercase letter")
        @Pattern(regexp = ".*[a-z].*", message = "Password have to contain a least one lowercase letter")
        @Pattern(regexp = ".*[0-9].*", message = "Password have to contain a least one number")
        String password,

        @Pattern(regexp = "^(?:\\+46\\s?\\d{1,3}-\\d{5,8}|0\\d{1,3}-\\d{5,8})$\n"
                , message = "Phone number to be in phone or mobile format, for example xxx-xxxxxxx.")
        String phoneNumber){
}
