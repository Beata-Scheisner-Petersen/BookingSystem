package org.example.bookingsystem.customer.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
public record CreateCustomerResponse(
        @NotBlank(message = "You must enter a firstname.")
        String firstname,

        @NotBlank(message = "You must enter a lastname.")
        String lastname,

        @NotBlank(message = "You must enter a email.")
        @Email(message = "Email format is invalid.")
        String email,

        @Pattern(regexp = "^(?:\\+46\\s?7\\d-\\d{7}|07\\d-\\d{7}|\\+46\\d{1,3}-\\d{5,8}|0\\d{1,3}-\\d{5,8})$",
                message = "Phone number to be in phone or mobile format, for example xxx-xxxxxxx.")
        String phoneNumber
) {
}
