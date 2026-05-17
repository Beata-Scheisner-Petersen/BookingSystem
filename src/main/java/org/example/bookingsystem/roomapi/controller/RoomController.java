package org.example.bookingsystem.roomapi.controller;

import org.example.bookingsystem.roomapi.dto.UpdateRoomDto;
import org.example.bookingsystem.roomapi.entity.Room;
import org.example.bookingsystem.roomapi.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/api/room/add")
    public ResponseEntity<?> roomSet(
            @RequestParam int roomNumber,
            @RequestParam int roomSize,
            @RequestParam BigDecimal roomPrice) {

        if (!roomService.addRoom(roomNumber, roomSize, roomPrice)) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
        return ResponseEntity.ok().body("Room saved successfully");
    }

    @PostMapping("/api/room/update/{id}")
    public ResponseEntity<?> roomUpdate(@PathVariable Long id,
                                        @RequestBody UpdateRoomDto roomDto) {

        if (!roomService.updateRoom(id, roomDto)) {
            return ResponseEntity.badRequest().body("Room could not be found");
        }
        return ResponseEntity.ok().body("Room updated successfully");
    }
}
