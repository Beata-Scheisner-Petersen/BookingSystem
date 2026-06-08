package org.example.bookingsystem.customer.service;

import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.customer.model.dto.CreateCustomerRequest;
import org.example.bookingsystem.customer.model.dto.CustomerUpdateRequest;
import org.example.bookingsystem.customer.repository.CustomerRepository;
import org.example.bookingsystem.exceptionhandler.customexeptions.*;
import org.example.bookingsystem.reservation.model.Reservation;
import org.example.bookingsystem.reservation.repository.ReservationRepository;
import org.example.bookingsystem.reservation.service.ReservationService;
import org.example.bookingsystem.security.password.PasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordService passwordService;
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    public CustomerService(CustomerRepository customerRepository,
                           PasswordService passwordService,
                           ReservationService reservationService,
                           ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.passwordService = passwordService;
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Customer createNewCustomer(CreateCustomerRequest request) {

        if (customerRepository.existsByEmail(request.email())) {
            throw new AlreadyExistException("Email already exist");
        } else if (customerRepository.existsByIdentificationNumber(request.identificationNumber())) {
            throw new AlreadyExistException("Identification number already exist in the system");
        } else if (request.phoneNumber() != null && customerRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new AlreadyExistException("Phone number already exist");
        }

        Customer customer = new Customer(
                request.firstname(),
                request.lastname(),
                request.identificationNumber(),
                request.email(),
                passwordService.hash(request.password()),
                request.phoneNumber());

        return customerRepository.save(customer);
    }

    public Customer loginCustomer(String email, String password) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new WrongEmailOrPasswordException("Wrong email or password"));

        if (!passwordService.matches(password, customer.getPassword())) {
            throw new WrongEmailOrPasswordException("Wrong email or password");
        }

        return customer;
    }

    @Transactional
    public void updateCustomerInfo(Long id, CustomerUpdateRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (request.email() != null && !request.email().isBlank()) {
            if (customerRepository.existsByEmail(request.email())) {
                throw new AlreadyExistException("Email already exist");
            }
            customer.setEmail(request.email());
        }

        if (request.phoneNumber() != null && !request.phoneNumber().isBlank()) {
            if (customerRepository.existsByPhoneNumber(request.phoneNumber())) {
                throw new AlreadyExistException("Phone number already exist");
            }
            customer.setPhoneNumber(request.phoneNumber());
        }

        if (request.firstname() != null) {
            customer.setFirstname(request.firstname());
        }

        if (request.lastname() != null) {
            customer.setLastname(request.lastname());
        }

        if (request.password() != null && !request.password().isBlank()) {
            customer.setPassword(passwordService.hash(request.password()));
        }

        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("not found"));
        //List<Reservation> reservationList = reservationService.getAllReservations();
        List<Reservation> reservationList = reservationService.getActiveReservationByCustomerId(customer.getId());

        if (!reservationList.isEmpty()) {
            throw new HaveReservationException("You cannot delete your account while you have active reservations.");
        }

        List<Reservation> oldReservations = reservationRepository.findAllByCustomer_Id(customer.getId());
        for (Reservation reservation : oldReservations) {
            reservation.setCustomer(null);
            reservationRepository.save(reservation);
        }

        customerRepository.delete(customer);
    }
}
