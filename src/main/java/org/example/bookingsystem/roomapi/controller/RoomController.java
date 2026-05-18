package org.example.bookingsystem.roomapi.controller;

import org.example.bookingsystem.roomapi.dto.AddNewRoomDto;
import org.example.bookingsystem.roomapi.dto.UpdateRoomDto;
import org.example.bookingsystem.roomapi.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class RoomController {

    private final RoomService service;

    public RoomController(RoomService roomService) {
        this.service = roomService;
    }

    @GetMapping("/api/room")
    public ResponseEntity<?>  getRooms(){
        if (service.getAllRooms().isEmpty()) {
            return ResponseEntity.status(400).body("No rooms found");
        }
        return ResponseEntity.ok(service.getAllRooms());
    }

    @PostMapping("/api/room/add")
    public ResponseEntity<?> roomSet(
            @RequestBody AddNewRoomDto addNewRoomDto) {

        if (!service.addRoom(
                addNewRoomDto.getRoomNumber(),
                addNewRoomDto.getRoomSize(),
                addNewRoomDto.getRoomPrice())
        ) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
        return ResponseEntity.ok().body("Room saved successfully");
    }

    @PostMapping("/api/room/update/{id}")
    public ResponseEntity<?> roomUpdate(@PathVariable Long id,
                                        @RequestBody UpdateRoomDto roomDto) {

        if (!service.updateRoom(id, roomDto)) {
            return ResponseEntity.badRequest().body("Room could not be found");
        }
        return ResponseEntity.ok().body("Room updated successfully");
    }
}
