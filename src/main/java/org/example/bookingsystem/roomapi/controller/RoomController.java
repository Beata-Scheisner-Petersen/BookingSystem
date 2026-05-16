package org.example.bookingsystem.roomapi.controller;

import org.example.bookingsystem.roomapi.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping("/api/test")
    public ResponseEntity<?> roomSet(
            @RequestParam int number,
            @RequestParam int size) {


        if (!roomService.saveRoom(number, size)) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
        return ResponseEntity.ok().body("Room saved successfully");


    }
}
