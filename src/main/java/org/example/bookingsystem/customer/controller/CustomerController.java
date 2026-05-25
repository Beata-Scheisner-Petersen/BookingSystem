package org.example.bookingsystem.customer.controller;

import jakarta.validation.Valid;

import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.customer.model.dto.CreateCustomerRequest;
import org.example.bookingsystem.customer.model.dto.CustomerLoginRequest;
import org.example.bookingsystem.customer.model.dto.CustomerUpdateRequest;
import org.example.bookingsystem.customer.service.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CreateCustomerRequest customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createNewCustomer(customer));
    }

    /*
     * Login -> create JWT
     * The password is checked against the database (via customerService + PasswordEncoder).
     * If that is correct, you will get a Customer object.
     * You call jwtService.generateToken(...) with:
     * customerId → is placed as the subject
     * email → is added as claim "email"
     * JwtService signs the token with your secret key (jwt.secret) and sets the expiration time.
     * Result: the client (frontend / Postman / Thunder client) receives a JWT string back (a token that is stored in the client).
     * For each protected request, the header is sent: Authorization: Bearer <your-token-here>
     * At Login, the request is sent to JwtAuthFilter.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerLoginRequest request) {
        Customer customer = customerService.loginCustomer(request.email(), request.password());

        return ResponseEntity.ok().body(customer.getId());
    }

    @PatchMapping("/me/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdateRequest request, @PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }

        customerService.updateCustomerInfo(id, request);

        return ResponseEntity.noContent().build();
    }
}
