package org.example.bookingsystem.reservation.service;

import jakarta.transaction.Transactional;
import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.customer.repository.CustomerRepository;
import org.example.bookingsystem.error.NotFoundException;
import org.example.bookingsystem.reservation.model.CreateReservationRequest;
import org.example.bookingsystem.reservation.model.Reservation;
import org.example.bookingsystem.reservation.model.ReservationStatus;
import org.example.bookingsystem.reservation.repository.ReservationRepository;
import org.example.bookingsystem.reservation.utils.Validations;
import org.example.bookingsystem.roomapi.entity.Room;
import org.example.bookingsystem.roomapi.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.example.bookingsystem.reservation.utils.Validations.validateDateRange;

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

    public void findActiveReservationByCustomerId (Long customerId){
        List <Reservation> reservationWithActiveStatus = reservationRepository.findByCustomer_IdAndStatus(customerId, ReservationStatus.ACTIVE);
    }

    //ToDO:
    //continue develop method validationRoomIsAvailable
    //total cost
    //visa atriva bookningare
    //skapa som visar akriva bookningar


}
