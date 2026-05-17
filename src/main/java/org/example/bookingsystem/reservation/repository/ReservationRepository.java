package org.example.bookingsystem.reservation.repository;

import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {
    List<Reservation> findAllByCustomer(Customer customer);
}
