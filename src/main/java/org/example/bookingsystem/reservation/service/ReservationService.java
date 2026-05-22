package org.example.bookingsystem.reservation.service;

import jakarta.transaction.*;
import org.example.bookingsystem.customer.model.*;
import org.example.bookingsystem.customer.repository.*;
import org.example.bookingsystem.exceptionhandler.customexeptions.*;
import org.example.bookingsystem.reservation.model.*;
import org.example.bookingsystem.reservation.repository.*;
import org.example.bookingsystem.roomapi.entity.*;
import org.example.bookingsystem.roomapi.repository.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

import static org.example.bookingsystem.reservation.utils.Validations.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, CustomerRepository customerRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }


    @Transactional
    public Reservation createReservation(CreateReservationRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Kunden finns inte"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NotFoundException("Rummet finns inte"));

        validateDateRange(request.getCheckIn(), request.getCheckOut());
        validationRoomIsAvailable(request.getRoomId(), request.getCheckIn(), request.getCheckOut(), null);
        Reservation reservation = new Reservation(
                customer,
                room,
                request.getExtraBed(),
                request.getCheckIn(),
                request.getCheckOut(),
                0

        );
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public void validationRoomIsAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut, Long bookingToIgnore) {
        List<Reservation> bookings = reservationRepository.findByRoom_IdAndStatusAndCheckInBeforeAndCheckOutAfter(
                roomId,
                ReservationStatus.ACTIVE,
                checkIn,
                checkOut
        );
    }

    //ToDO:
    //continue develop method validationRoomIsAvailable
    //total cost method


}
