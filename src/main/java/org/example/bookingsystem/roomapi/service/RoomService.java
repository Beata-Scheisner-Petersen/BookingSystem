package org.example.bookingsystem.roomapi.service;

import org.example.bookingsystem.roomapi.dto.RoomResponseDto;
import org.example.bookingsystem.roomapi.dto.UpdateRoomDto;
import org.example.bookingsystem.roomapi.entity.Room;
import org.example.bookingsystem.roomapi.repository.RoomRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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
    }

    public RoomResponseDto getRoomById(long id) {

        Optional<Room> optionalRoom = repository.findById(id);

        if (optionalRoom.isEmpty()) {
            return null;
        }

        Room room = optionalRoom.get();

        return new RoomResponseDto(
                room.getId(),
                room.getRoomNumber(),
                room.getRoomSize(),
                room.getRoomPrice()
        );

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

    public RoomResponseDto updateRoom(Long id, UpdateRoomDto dto) {

        Optional<Room> optionalRoom = repository.findById(id);

        if (optionalRoom.isEmpty()) {
            return null;
        }

        Room fetchedRoom = optionalRoom.get();

        fetchedRoom.setRoomNumber(dto.getRoomNumber());
        fetchedRoom.setRoomSize(dto.getRoomSize());
        fetchedRoom.setRoomPrice(dto.getRoomPrice());

        Room resultRoom = repository.save(fetchedRoom);

        return new RoomResponseDto(
                resultRoom.getId(),
                resultRoom.getRoomNumber(),
                resultRoom.getRoomSize(),
                resultRoom.getRoomPrice()
        );

        return resultDto;

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
