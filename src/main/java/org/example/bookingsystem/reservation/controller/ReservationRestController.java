package org.example.bookingsystem.reservation.controller;

import jakarta.validation.Valid;
import org.example.bookingsystem.reservation.model.CreateAvailabilityRequest;
import org.example.bookingsystem.reservation.model.CreateReservationRequest;
import org.example.bookingsystem.reservation.model.Reservation;
import org.example.bookingsystem.reservation.model.UpdateReservationRequest;
import org.example.bookingsystem.reservation.service.ReservationService;
import org.example.bookingsystem.roomapi.entity.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservation")
public class ReservationRestController {
    private final ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
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

    @PutMapping("/{reservationId}")
    public ResponseEntity<Reservation>updateReservation (@PathVariable Long reservationId, @Valid @RequestBody UpdateReservationRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.updateReservation(reservationId, request.getCheckIn(), request.getCheckOut()));
    }

    @PostMapping("/available")
    public List<Room> getAvailableRooms(@Valid @RequestBody CreateAvailabilityRequest request) {

        return reservationService.getAvailableRooms(request);
    }






}
