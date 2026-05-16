package org.example.bookingsystem.roomapi.service;

import org.example.bookingsystem.roomapi.entity.Room;
import org.example.bookingsystem.roomapi.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public boolean saveRoom(int roomNumber, int roomSize) {
        try {
            repository.save(new Room(roomNumber, roomSize));
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
