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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseEntity<?> login(
            @Valid @RequestBody CustomerLoginRequest request,
            BindingResult result,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        Customer customer = customerService.loginCustomer(request.email(), request.password());

        session.setAttribute("customerId", customer.getId());

        return ResponseEntity.ok().body(Map.of("message", "login successful"));
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateCustomer(HttpSession session, CustomerUpdateRequest request) {
        Long id = (Long) session.getAttribute("customerId");

        if (id == null) {
            return ResponseEntity.status(302)
                    .header("Location", "/login")
                    .build();
        }

        customerService.updateCustomerInfo(id, request);

        return ResponseEntity.status(302)
                .header("Location", "/mypage")
                .build();
    }


    @DeleteMapping
    public ResponseEntity<?> deleteCustomer(HttpSession session) {

        Long id = (Long) session.getAttribute("customerId");

        if (id == null) {
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body(Map.of("error", "authorization failed"));
        }

        customerService.deleteCustomer(id);
        return ResponseEntity.ok().body(Map.of("message", "account deleted"));
    }
}
