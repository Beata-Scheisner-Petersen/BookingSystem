package org.example.bookingsystem.reservation.repository;

import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.reservation.model.Reservation;
import org.example.bookingsystem.reservation.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {
    List<Reservation> findAllByCustomer(Customer customer);

    List<Reservation> findByRoom_IdAndStatusAndCheckInBeforeAndCheckOutAfter (
            Long roomId,
            ReservationStatus reservationStatus,
            LocalDate checkIn,
            LocalDate checkOut
    );

    List<Reservation> findByCustomer_IdAndStatus(long customerId, ReservationStatus status);

    List<Reservation> findAllByCustomer_Id(Long customerId);
}

