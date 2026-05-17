package org.example.bookingsystem.reservation.model;
import jakarta.persistence.*;
import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.roomapi.entity.Room;
import java.time.LocalDate;

@Entity
@Table (name= "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name="customer_id", updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "room_id", updatable = false)
    private Room room;

    private boolean isReserved;
    private boolean extraBed;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalCost;

    public Reservation (){
    }

    public Reservation(Customer customer, Room room, boolean isReserved, boolean extraBed, LocalDate checkIn, LocalDate checkOut, double totalCost) {
        this.customer = customer;
        this.room = room;
        this.isReserved = isReserved;
        this.extraBed = extraBed;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalCost = totalCost;
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

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public boolean isExtraBed() {
        return extraBed;
    }

    public void setExtraBed(boolean extraBed) {
        this.extraBed = extraBed;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
