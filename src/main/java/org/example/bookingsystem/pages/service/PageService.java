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
    private final ReservationRepository reservationRepository;

    public PageService(CustomerRepository customerRepository, ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    public CustomerInfoRequest getCustomer(Long id) {
        return customerRepository
                .getCustomersById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public List<GetReservationInfo> getCustomerReservations(Long CustomerId) {
        Customer customer = customerRepository.findById(CustomerId).orElseThrow(() -> new NotFoundException("Customer not found"));
        return reservationRepository
                .findAllByCustomer(customer)
                .stream()
                .map(
                        r -> new GetReservationInfo(
                                r.getCheckIn(),
                                r.getCheckOut(),
                                r.getRoom(),
                                r.isExtraBed(),
                                r.getTotalCost(),
                                r.getStatus()
                        )
                ).toList();
    }
}
