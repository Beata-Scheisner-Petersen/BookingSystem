package org.example.bookingsystem.roomapi.dto;

import java.math.BigDecimal;

public record RoomResponseDto(Long id, int roomNumber, int roomSize, BigDecimal roomPrice) {
}
