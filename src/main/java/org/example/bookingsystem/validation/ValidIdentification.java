package org.example.bookingsystem.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdentificationValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdentification {

    String message() default "Invalid identification number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
