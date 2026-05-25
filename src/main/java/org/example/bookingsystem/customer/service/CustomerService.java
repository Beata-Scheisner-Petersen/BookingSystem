package org.example.bookingsystem.customer.service;

import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.customer.model.dto.CreateCustomerRequest;
import org.example.bookingsystem.customer.model.dto.CustomerUpdateRequest;
import org.example.bookingsystem.customer.repository.CustomerRepository;

import org.example.bookingsystem.exceptionhandler.customexeptions.AlreadyExistException;
import org.example.bookingsystem.exceptionhandler.customexeptions.BadRequestException;
import org.example.bookingsystem.exceptionhandler.customexeptions.WrongEmailOrPasswordException;

import org.example.bookingsystem.reservation.service.ReservationService;
import org.example.bookingsystem.security.password.PasswordService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final PasswordService passwordService;
    private final ReservationService reservationService;

    public CustomerService(CustomerRepository repository,
                           PasswordService passwordService,
                           ReservationService reservationService) {
        this.repository = repository;
        this.passwordService = passwordService;
        this.reservationService = reservationService;
    }

    @Transactional
    public Customer createNewCustomer(CreateCustomerRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new AlreadyExistException("Email already exist");
        } else if (repository.existsByIdentificationNumber(request.identificationNumber())) {
            throw new AlreadyExistException("Identification number already exist in the system");
        } else if (request.phoneNumber() != null && repository.existsByPhoneNumber(request.phoneNumber())) {
            throw new AlreadyExistException("Phone number already exist");
        }

        Customer customer = new Customer(
                request.firstname(),
                request.lastname(),
                request.identificationNumber(),
                request.email(),
                passwordService.hash(request.password()),
                request.phoneNumber());

        return repository.save(customer);
    }

    public Customer loginCustomer(String email, String password) {
        Customer customer = repository.findByEmail(email)
                .orElseThrow(() -> new WrongEmailOrPasswordException("Wrong email or password"));

        if (!passwordService.matches(password, customer.getPassword())) {
            throw new WrongEmailOrPasswordException("Wrong email or password");
        }

        return customer;
    }

    @Transactional
    public void updateCustomerInfo(Long id, CustomerUpdateRequest request) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (request.email() != null && !request.email().isBlank()) {
            if (repository.existsByEmail(request.email())) {
                throw new AlreadyExistException("Email already exist");
            }
            customer.setEmail(request.email());
        }

        if (request.phoneNumber() != null && !request.phoneNumber().isBlank()) {
            if (repository.existsByPhoneNumber(request.phoneNumber())) {
                throw new AlreadyExistException("Phone number already exist");
            }
            customer.setPhoneNumber(request.phoneNumber());
        }

        if (request.password() != null && !request.password().isBlank()) {
            customer.setPassword(passwordService.hash(request.password()));
        }

        repository.save(customer);
    }

    public void removeCustomer(Long id) {
        if (!repository.existsById(id) || reservationService.getActiveReservationByCustomerId(id) == null) {
            throw new BadRequestException("You cannot delete the account while you have active bookings.");
        }
        repository.deleteById(id);
    }
}
