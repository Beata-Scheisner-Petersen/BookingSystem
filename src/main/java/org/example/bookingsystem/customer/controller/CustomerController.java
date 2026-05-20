package org.example.bookingsystem.customer.controller;

import jakarta.validation.*;
import org.example.bookingsystem.customer.model.*;
import org.example.bookingsystem.customer.model.dto.*;
import org.example.bookingsystem.customer.service.*;
import org.example.bookingsystem.security.jwt.*;
import org.example.bookingsystem.security.jwt.dto.*;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createNewCustomer(customer, customer.email()));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody CustomerLoginRequest request, HttpSession session) {
        Customer customer = service.loginCustomer(request.email(), request.password());
        session.setAttribute("customerId", customer.getId());
        return ResponseEntity.ok("Logged in");
    }

    @PatchMapping("/me")
    public ResponseEntity updateCustomer(@RequestBody CustomerUpdateRequest request,
                                         HttpSession session) {
        Long id = (Long) session.getAttribute("customerId");

        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }

        service.updateCustomerInfo(id, request);
        return ResponseEntity.noContent().build();
    }
}
