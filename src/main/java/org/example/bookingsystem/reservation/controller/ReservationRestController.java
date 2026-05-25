package org.example.bookingsystem.reservation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.example.bookingsystem.reservation.model.CreateReservationRequest;
import org.example.bookingsystem.reservation.model.Reservation;
import org.example.bookingsystem.reservation.model.UpdateReservationRequest;
import org.example.bookingsystem.reservation.service.ReservationService;
import org.example.bookingsystem.roomapi.entity.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping()
    public List<Room> getAvailableRooms(
            @RequestParam @NotNull LocalDate checkIn,
            @RequestParam @NotNull LocalDate checkOut,
            @RequestParam @Min(1) int guests)      
    {
        return  reservationService.getAvailableRooms(checkIn, checkOut, guests);
    }






}
