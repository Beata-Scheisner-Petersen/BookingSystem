package org.example.bookingsystem.roomapi.service;

import org.example.bookingsystem.roomapi.dto.UpdateRoomDto;
import org.example.bookingsystem.roomapi.entity.Room;
import org.example.bookingsystem.roomapi.repository.RoomRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    //Rooms are sorted ascending by their roomNumber
    public List<Room> getAllRooms() {

        return repository.findAll(Sort.by("roomNumber").ascending());

        //Pageable pageable = PageRequest.of(page, amount);
        //return repository.findAll(pageable).getContent();
    }

    public Room getRoomById(long id) {
        return repository.findById(id).orElse(null);
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
