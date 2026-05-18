package org.example.bookingsystem.reservation.service;

import org.example.bookingsystem.reservation.model.CreateReservationRequest;
import org.example.bookingsystem.reservation.model.Reservation;

public class ReservationService {
private Reservation reservation;
    public Reservation makeReservation (CreateReservationRequest request){
        return new Reservation();
    }
}
