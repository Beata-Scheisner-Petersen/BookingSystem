package org.example.bookingsystem.security.jwt.service;

import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.customer.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/*
* Is the link between a database (CustomerRepository) and Spring Security's authentication system.
* @Service → Spring creates an instance and registers it as a bean.
* implements UserDetailsService → you tell Spring Security: 'When you need to load a user, use me.'
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public MyUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /*
    * Fetching the customer from the database.
    * Convert your Customer into an object that Spring Security understands.
    * Creates a User object (Spring Security's built-in implementation of UserDetails).
    * .authorities(new ArrayList<>()) -> because where are no roles yet
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(customer.getEmail())
                .password(customer.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }
}
