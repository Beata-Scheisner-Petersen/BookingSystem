package org.example.bookingsystem.customer.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.customer.model.dto.CreateCustomerRequest;
import org.example.bookingsystem.customer.model.dto.CustomerLoginRequest;
import org.example.bookingsystem.customer.model.dto.CustomerUpdateRequest;
import org.example.bookingsystem.customer.service.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerLoginRequest request, HttpSession session) {
        Customer customer = customerService.loginCustomer(request.email(), request.password());

        session.setAttribute("customerId", customer.getId());

        return ResponseEntity.ok().body(Map.of("message", "login successful"));
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdateRequest request, HttpSession session) {
        Long id = (Long) session.getAttribute("customerId");

        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }

        customerService.updateCustomerInfo(id, request);

        return ResponseEntity.ok().body(Map.of("message", "Customer info updated"));
    }
}
