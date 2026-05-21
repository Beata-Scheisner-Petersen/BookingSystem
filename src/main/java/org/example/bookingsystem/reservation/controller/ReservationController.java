package org.example.bookingsystem.reservation.controller;

import jakarta.validation.Valid;
import org.example.bookingsystem.reservation.model.CreateReservationRequest;
import org.example.bookingsystem.reservation.model.Reservation;
import org.example.bookingsystem.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody CreateReservationRequest request) {
        Reservation createReservation = reservationService.createReservation(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createReservation);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Reservation> cancelReservation(@PathVariable Long reservationId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.cancelReservation(reservationId));
    }


}
