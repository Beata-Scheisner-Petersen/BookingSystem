package org.example.bookingsystem.customer.model.dto;

public record CustomerInfoRequest(
        String firstname,
        String lastname,
        String email,
        String phoneNumber
) {
}
