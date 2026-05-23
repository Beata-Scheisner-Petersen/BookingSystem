package org.example.bookingsystem.roomapi.service;

import org.example.bookingsystem.roomapi.dto.RoomResponseDto;
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
    private final RoomResponseDto roomResponseDto;

    public RoomService(RoomRepository repository, RoomResponseDto roomResponseDto) {
        this.repository = repository;
        this.roomResponseDto = roomResponseDto;
    }

    //Rooms are sorted ascending by their roomNumber
    public List<Room> getAllRooms() {
        return repository.findAll(Sort.by("roomNumber").ascending());
    }

    public Room getRoomById(long id) {
        return repository.findById(id).orElse(null);
    }

    public RoomResponseDto addRoom(int roomNumber, int roomSize, BigDecimal roomPrice) {
            Room returnedRoom = repository.save(new Room(roomNumber, roomSize, roomPrice));

            return new RoomResponseDto(
                    returnedRoom.getId(),
                    returnedRoom.getRoomNumber(),
                    returnedRoom.getRoomSize(),
                    returnedRoom.getRoomPrice()
            );

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

    public boolean deleteRoom(Long id) {
        Optional<Room> optionalRoom = repository.findById(id);
        if (optionalRoom.isEmpty()) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
