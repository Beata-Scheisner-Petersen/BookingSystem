package org.example.bookingsystem.customer.controller;

import jakarta.validation.Valid;

import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.customer.model.dto.CreateCustomerRequest;
import org.example.bookingsystem.customer.model.dto.CreateCustomerResponse;
import org.example.bookingsystem.customer.model.dto.CustomerLoginRequest;
import org.example.bookingsystem.customer.model.dto.CustomerUpdateRequest;
import org.example.bookingsystem.customer.service.CustomerService;
import org.example.bookingsystem.security.jwt.model.CustomUserDetails;
import org.example.bookingsystem.security.jwt.model.dto.JwtResponse;
import org.example.bookingsystem.security.jwt.service.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<CreateCustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        Customer savedCustomer = customerService.createNewCustomer(request);
        return ResponseEntity
                .status(HttpStatus.CREATED).
                        body(new CreateCustomerResponse(
                                savedCustomer.getFirstname(),
                                savedCustomer.getLastname(),
                                savedCustomer.getEmail(),
                                savedCustomer.getPhoneNumber()
                ));
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
        Customer customer = customerService
                .loginCustomer(request.email(), request.password());
        String token = jwtService
                .generateToken(customer.getId(), customer.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateCustomer(@AuthenticationPrincipal CustomUserDetails user,
                                            @RequestBody CustomerUpdateRequest request) {

        Long id = user.getId();

        if (id == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "You are not authorized"));
        }

        customerService.updateCustomerInfo(id, request);

        return ResponseEntity.ok(Map.of("message", "Info is updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@AuthenticationPrincipal CustomUserDetails user) {
        Long id = user.getId();

        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(Map.of("error", "You are not authorized"));
        }
        customerService.removeCustomer(id);

        return ResponseEntity.ok(Map.of("message", "Account deleted"));
    }
}
