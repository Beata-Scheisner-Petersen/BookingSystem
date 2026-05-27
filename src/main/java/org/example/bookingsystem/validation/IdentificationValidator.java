package org.example.bookingsystem.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IdentificationValidator implements ConstraintValidator<ValidIdentification, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return false;
        }

        // Format: YYMMDD-XXXX or YYYYMMDD-XXXX
        if (!value.matches("^(\\d{6}|\\d{8})-\\d{4}$")) {
            return false;
        }

        // Extract date part
        String datePart = value.split("-")[0];

        // Convert 6-digit to 8-digit (assume 19xx or 20xx)
        if (datePart.length() == 6) {
            String year = datePart.substring(0, 2);
            int yearInt = Integer.parseInt(year);

            // Simple rule: 00–24 → 2000–2024, else 1900–1999
            datePart = (yearInt <= 24 ? "20" : "19") + datePart;
        }

        // Validate date
        try {
            LocalDate.parse(datePart, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
