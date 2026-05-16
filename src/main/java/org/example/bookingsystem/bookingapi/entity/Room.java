package org.example.bookingsystem.bookingapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "room")
public class Room {

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

    public Room(int roomNumber, int roomSize) {
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
    }

    public Room() {

    }

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
}
