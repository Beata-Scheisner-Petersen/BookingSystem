package org.example.bookingsystem.roomapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Entity
@Table(name = "room")
public class Room {

    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "room_number", nullable = false, unique = true)
    @Positive(message = "Room number can't be 0 or Negative")
    private int roomNumber;

    @Column(name = "room_size", nullable = false)
    @Positive(message = "Room size can't be 0 or Negative")
    private int roomSize;

    @Column(name = "room_price", nullable = false)
    @PositiveOrZero
    private BigDecimal roomPrice;

    //Constructors
    public Room() {
    }

    public Room(int roomNumber, int roomSize, BigDecimal roomPrice) {
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.roomPrice =  roomPrice;
    }

    //Get - Set
    public Long getId() {
        return id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public BigDecimal getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(BigDecimal roomPrice) {
        this.roomPrice = roomPrice;
    }
}
