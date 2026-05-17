package org.example.bookingsystem.customer.controller;

import jakarta.validation.*;
import org.example.bookingsystem.customer.model.*;
import org.example.bookingsystem.customer.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CreateCustomerRequest customer, String email) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNewCustomer(customer, email));
    }
}
