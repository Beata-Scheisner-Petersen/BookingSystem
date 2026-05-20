package org.example.bookingsystem.customer.service;

import org.example.bookingsystem.customer.model.*;
import org.example.bookingsystem.customer.model.dto.*;
import org.example.bookingsystem.customer.repository.*;
import org.example.bookingsystem.exceptionhandler.customexeptions.*;
import org.example.bookingsystem.security.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final PasswordService passwordService;

    public CustomerService(CustomerRepository repository, PasswordService passwordService) {
        this.repository = repository;
        this.passwordService = passwordService;
    }

    @Transactional
    public Customer createNewCustomer(CreateCustomerRequest request, String email) {

       if (repository.existsByEmail(email)) {
           throw new CustomerExistException("Customer already exist");
       }
       Customer customer = new Customer(
               request.firstname(),
               request.lastname(),
               request.identificationNumber(),
               request.email(),
               passwordService.hash(request.password()));
       return repository.save(customer);
    }

    public Customer loginCustomer(String email, String password) {
        Customer customer = repository.findByEmail(email).orElseThrow(() -> new WrongEmailOrPasswordException("Wrong email or password"));

        if (!passwordService.matches(password, customer.getPassword())) {
            throw new WrongEmailOrPasswordException("Wrong email or password");
        }

        return customer;
    }

    @Transactional
    public void updateCustomerInfo(Long id, CustomerUpdateRequest request) {
        Customer customer = repository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

        if (request.email() != null) {
            customer.setEmail(request.email());
        }

        if (request.phoneNumber() != null) {
            customer.setPhoneNumber(request.phoneNumber());
        }

        if (request.password() != null) {
            customer.setPassword(passwordService.hash(request.password()));
        }

        repository.save(customer);
    }
}
