package org.example.bookingsystem.pages.service;

import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.customer.model.dto.CustomerInfoRequest;
import org.example.bookingsystem.customer.repository.CustomerRepository;
import org.example.bookingsystem.exceptionhandler.customexeptions.NotFoundException;
import org.example.bookingsystem.reservation.model.GetReservationInfo;
import org.example.bookingsystem.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {
    private final CustomerRepository customerRepository;

    public PageService(CustomerRepository customerRepository, ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerInfoRequest getCustomer(Long id) {
        return customerRepository
                .getCustomersById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

}
