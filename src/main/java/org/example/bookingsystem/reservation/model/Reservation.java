package org.example.bookingsystem.reservation.model;
import jakarta.persistence.*;
import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.roomapi.entity.Room;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table (name= "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne (optional = false)
    @JoinColumn (name="customer_id", updatable = false)
    private Customer customer;

    @ManyToOne (optional = false)
    @JoinColumn(name = "room_id", updatable = false)
    private Room room;

    private boolean extraBed;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BigDecimal totalCost;
    private int guests;

    @Enumerated (EnumType.STRING)
    private ReservationStatus status;

    public Reservation (){
    }

    public Reservation(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut, BigDecimal totalCost, ReservationStatus status, int guests) {
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalCost = totalCost;
        this.status = status;
        this.guests = guests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus reservationStatus) {
        this.status = reservationStatus;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }
}
