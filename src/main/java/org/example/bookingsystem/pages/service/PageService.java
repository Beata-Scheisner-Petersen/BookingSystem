package org.example.bookingsystem.pages.service;

import org.example.bookingsystem.customer.model.dto.CustomerInfoRequest;
import org.example.bookingsystem.customer.repository.CustomerRepository;
import org.example.bookingsystem.exceptionhandler.customexeptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PageService {
    private final CustomerRepository customerRepository;

    public PageService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerInfoRequest getCustomer(Long id) {
        return customerRepository
                .getCustomersById(id)
                .orElseThrow(() ->
                        new NotFoundException("Customer not found"));
    }

}
