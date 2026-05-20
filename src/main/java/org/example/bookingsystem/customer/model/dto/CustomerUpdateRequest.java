package org.example.bookingsystem.customer.model.dto;

import jakarta.validation.constraints.*;
public record CustomerUpdateRequest(

        @NotBlank(message = "You must enter a email.")
        @Email(message = "Email format is invalid.")
        String email,

        @NotBlank(message = "You must enter a password.")
        String password,

        @Pattern(regexp = "^(?:\\+46\\s?7\\d-\\d{7}|07\\d-\\d{7}|\\+46\\d{1,3}-\\d{5,8}|0\\d{1,3}-\\d{5,8})$\n"
                , message = "Phone number to be in phone or mobile format, for example xxx-xxxxxxx.")
        String phoneNumber
) {
}
