package org.example.bookingsystem.customer.controller;

import jakarta.validation.*;
import org.example.bookingsystem.customer.model.*;
import org.example.bookingsystem.customer.model.dto.*;
import org.example.bookingsystem.customer.service.*;
import org.example.bookingsystem.security.jwt.dto.*;
import org.example.bookingsystem.security.jwt.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final JwtService jwtService;

    public CustomerController(CustomerService customerService, JwtService jwtService) {
        this.customerService = customerService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CreateCustomerRequest customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createNewCustomer(customer));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody CustomerLoginRequest request) {
        Customer customer = customerService.loginCustomer(request.email(), request.password());
        String token = jwtService.generateToken(customer.getId(), customer.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PatchMapping("/me")
    public ResponseEntity updateCustomer(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CustomerUpdateRequest request) {

        String token = authHeader.replace("Bearer ", "");

        Long id = jwtService.extractCustomerId(token);

        String email = jwtService.extractEmail(token);

        if (id == null || email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }

        customerService.updateCustomerInfo(id, request);
        return ResponseEntity.noContent().build();
    }
}
