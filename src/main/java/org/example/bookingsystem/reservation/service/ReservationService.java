package org.example.bookingsystem.reservation.service;

import jakarta.transaction.Transactional;
import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.customer.repository.CustomerRepository;
import org.example.bookingsystem.exceptionhandler.customexeptions.NotFoundException;
import org.example.bookingsystem.reservation.model.CreateReservationRequest;
import org.example.bookingsystem.reservation.model.Reservation;
import org.example.bookingsystem.reservation.model.ReservationStatus;
import org.example.bookingsystem.reservation.model.dto.GetAllCustomerReservationsDto;
import org.example.bookingsystem.reservation.repository.ReservationRepository;
import org.example.bookingsystem.roomapi.entity.Room;
import org.example.bookingsystem.roomapi.repository.RoomRepository;
import org.example.bookingsystem.roomapi.service.RoomService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import static org.example.bookingsystem.reservation.utils.Validations.validateDateRange;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;

    public ReservationService(ReservationRepository reservationRepository,
                              CustomerRepository customerRepository,
                              RoomRepository roomRepository,
                              RoomService roomService
    ) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


    public List<Reservation> getActiveReservationByCustomerId(Long customerId) {

        return reservationRepository.findByCustomer_IdAndStatus(
                customerId,
                ReservationStatus.ACTIVE
        );
    }

    public List<GetAllCustomerReservationsDto> getAllReservationByCustomerId(Long customerId) {

        return reservationRepository.findAllByCustomer_Id(customerId)
                .stream()
                .sorted(Comparator.comparing(
                        reservation -> reservation.getStatus() != ReservationStatus.ACTIVE)
                ).map(
                        reservation -> new GetAllCustomerReservationsDto(
                                reservation.getId(),
                                reservation.getCheckIn(),
                                reservation.getCheckOut(),
                                reservation.getRoom().getRoomNumber(),
                                reservation.getTotalCost(),
                                reservation.getStatus()
                        )
                ).toList();
    }


    @Transactional
    public Reservation createReservation(CreateReservationRequest request) {

        Customer customer = customerRepository
                .findById(request.getCustomerId())
                .orElseThrow(
                        () -> new NotFoundException("Kunden finns inte")
                );

        Room room = roomRepository
                .findById(request.getRoomId())
                .orElseThrow(
                        () -> new NotFoundException("Rummet finns inte")
                );

        validateDateRange(
                request.getCheckIn(),
                request.getCheckOut()
        );

        validationRoomIsAvailable(
                request.getRoomId(),
                request.getCheckIn(),
                request.getCheckOut(),
                null
        );

        validateRoomCapacity(
                room,
                request.getGuests()
        );

        Reservation reservation = new Reservation(
                customer,
                room,
                request.getCheckIn(),
                request.getCheckOut(),
                countTotalPrice(
                        room,
                        request.getCheckIn(),
                        request.getCheckOut(),
                        request.getGuests()
                ),
                ReservationStatus.ACTIVE,
                request.getGuests()
        );

        return reservationRepository.save(reservation);
    }


    public void validationRoomIsAvailable(Long roomId,
                                          LocalDate checkIn,
                                          LocalDate checkOut,
                                          Long bookingToIgnore
    ) {
        List<Reservation> bookings = reservationRepository.findByRoom_IdAndStatusAndCheckInBeforeAndCheckOutAfter(
                roomId,
                ReservationStatus.ACTIVE,
                checkOut,
                checkIn
        );

        if (bookingToIgnore != null) {
            bookings = bookings.stream()
                    .filter(
                            b -> !b.getId().equals(bookingToIgnore)
                    ).toList()
            ;
        }
        if (!bookings.isEmpty()) {
            throw new IllegalArgumentException(
                    "Room is already booked for selected dates"
            );
        }
    }

    private void validateRoomCapacity(Room room, int requestedGuests) {
        int maxCapacity = room.getMaxGuests();

        if (room.isExtraBedAvailable()) {
            maxCapacity += 1;
        }

        if (requestedGuests > maxCapacity) {
            throw new IllegalArgumentException(
                    "This room can accommodate a maximum of " + maxCapacity + " guests."
            );
        }
    }

    public BigDecimal countTotalPrice(Room room,
                                      LocalDate checkIn,
                                      LocalDate checkOut,
                                      int guests) {
        long days = ChronoUnit.DAYS.between(
                checkIn,
                checkOut
        );

        BigDecimal extraBedPricePerDay = BigDecimal.ZERO;

        if (guests > room.getMaxGuests()) {
            extraBedPricePerDay = BigDecimal.valueOf(500);
        }

        BigDecimal roomPricePerDay = room.getRoomPrice();

        //Price for summer -  add 30%
        BigDecimal extraPriceForHighSeason = BigDecimal.ONE;

        if (checkIn.getMonthValue() >= 6 && checkIn.getMonthValue() <= 8) {

            extraPriceForHighSeason = BigDecimal.valueOf(1.3);
        }
        return (roomPricePerDay
                .add(extraBedPricePerDay)
                .multiply(extraPriceForHighSeason)
                .multiply(BigDecimal.valueOf(days))
        );
    }

    public Reservation cancelReservation(Long reservationId) {

        Reservation reservation = getReservationById(reservationId);

        reservation.setStatus(
                ReservationStatus.CANCELED
        );

        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(
                        () -> new NotFoundException("Reservation finns inte")
                );
    }

    public Reservation updateReservation(Long reservationId, LocalDate checkIn, LocalDate checkOut) {

        Reservation reservation = getReservationById(reservationId);

        validationRoomIsAvailable(
                reservation.getRoom().getId(),
                checkIn,
                checkOut,
                reservationId
        );

        reservation.setCheckIn(checkIn);

        reservation.setCheckOut(checkOut);

        reservation.setTotalCost(
                countTotalPrice(
                        reservation.getRoom(),
                        checkIn, checkOut,
                        reservation.getGuests()
                )
        );

        return reservationRepository.save(reservation);
    }


    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut, int guests) {
        validateDateRange(checkIn, checkOut);

        return roomService.getAllRooms()
                .stream()
                .filter(
                        room -> {
                            int maxCapacity = room.getMaxGuests();

                            // add extra place only if room supports extra bed
                            if (room.isExtraBedAvailable()) {
                                maxCapacity += 1;
                            }

                            return maxCapacity >= guests;
                        }
                ).filter(
                        room -> {
                            try {
                                validationRoomIsAvailable(
                                        room.getId(),
                                        checkIn,
                                        checkOut,
                                        null
                                );
                                return true;
                            } catch (RuntimeException e) {
                                return false;
                            }
                        }
                ).toList();
    }
}


