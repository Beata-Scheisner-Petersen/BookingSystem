package org.example.bookingsystem.roomapi.service;

import org.example.bookingsystem.roomapi.dto.UpdateRoomDto;
import org.example.bookingsystem.roomapi.entity.Room;
import org.example.bookingsystem.roomapi.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public boolean addRoom(int roomNumber, int roomSize, BigDecimal roomPrice) {
        try {
            repository.save(new Room(roomNumber, roomSize, roomPrice));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateRoom(Long id, UpdateRoomDto dto) {

        Optional<Room> optionalRoom = repository.findById(id);

        if (optionalRoom.isEmpty()) {
            return false;
        }

        Room fetchedRoom = optionalRoom.get();

        fetchedRoom.setRoomNumber(dto.getRoomNumber());
        fetchedRoom.setRoomSize(dto.getRoomSize());
        fetchedRoom.setRoomPrice(dto.getRoomPrice());

        repository.save(fetchedRoom);

        return true;

    }
}
