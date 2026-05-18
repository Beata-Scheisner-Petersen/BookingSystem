package org.example.bookingsystem.customer.repository;

import org.example.bookingsystem.customer.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);
}
