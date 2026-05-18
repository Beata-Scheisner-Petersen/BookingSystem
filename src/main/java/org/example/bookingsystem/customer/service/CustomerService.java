package org.example.bookingsystem.customer.service;

import org.example.bookingsystem.customer.model.*;
import org.example.bookingsystem.customer.model.dto.*;
import org.example.bookingsystem.customer.repository.*;
import org.example.bookingsystem.exceptionhandler.customexeptions.*;
import org.example.bookingsystem.security.*;
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

}
