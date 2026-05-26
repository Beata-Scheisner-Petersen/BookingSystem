package org.example.bookingsystem.roomapi.service;

import org.example.bookingsystem.roomapi.dto.RoomResponseDto;
import org.example.bookingsystem.roomapi.dto.UpdateRoomDto;
import org.example.bookingsystem.roomapi.entity.Room;
import org.example.bookingsystem.roomapi.repository.RoomRepository;
import org.hibernate.boot.internal.Extends;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    List<Room> fakeRooms;
    Room room1;
    Room room2;
    Room room3;

    @BeforeEach
    void setUp() {
        room1 = new Room();
        room1.setRoomNumber(101);

        room2 = new Room();
        room2.setRoomNumber(102);

        room3 = new Room();
        room3.setRoomNumber(103);
    }

    @Test
    void getAllRooms() {
        //Arrange
        fakeRooms = List.of(room1, room2, room3);
        Mockito.when(roomRepository.findAll())
                .thenReturn(fakeRooms);

        //Act
        List<Room> result = roomService.getAllRooms();

        //Assert //Needs to assert that it was used with the correct Sort
        assertEquals(3, result.size());
    }

    @Test
    void getRoomByIdFound() {
        //Arrange
        fakeRooms = List.of(room1, room2, room3);
        Mockito.when(roomRepository.findById(1L)).thenReturn(Optional.of(fakeRooms.get(0)));
        Mockito.when(roomRepository.findById(2L)).thenReturn(Optional.of(fakeRooms.get(1)));
        Mockito.when(roomRepository.findById(3L)).thenReturn(Optional.of(fakeRooms.get(2)));

        //Act
        RoomResponseDto result1 = roomService.getRoomById(1L);
        RoomResponseDto result2 = roomService.getRoomById(2L);
        RoomResponseDto result3 = roomService.getRoomById(3L);

        //Assert
        assertEquals(101, result1.roomNumber());
        assertEquals(102, result2.roomNumber());
        assertEquals(103, result3.roomNumber());
    }

    @Test
    void getRoomByIdNotFound() {
        //Arrange
        Mockito.when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        RoomResponseDto result1 = roomService.getRoomById(1L);

        //Assert
        assertNull(result1);

    }

    @Test
    void addANewRoomSuccess() {
        //Arrange
        Room fakeRoom = new Room(104, 4, BigDecimal.valueOf(1000));
        Mockito.when(roomRepository.save(Mockito.any(Room.class)))
                .thenReturn(fakeRoom);

        //Act
        RoomResponseDto result = roomService.addRoom(104, 4, BigDecimal.valueOf(1000));

        //Assert
        assertEquals(104, result.roomNumber());
        assertEquals(4, result.roomSize());
        assertEquals(BigDecimal.valueOf(1000), result.roomPrice());
    }

    @Test
    void addANewRoomFailure() {
        //Arrange
        Room fakeRoom = new Room(104, 4, BigDecimal.valueOf(1000));
        Mockito.when(roomRepository.save(Mockito.any(Room.class)))
                .thenThrow(new RuntimeException("Something went wrong"));
        //Act
        RoomResponseDto result = roomService.addRoom(104, 4, BigDecimal.valueOf(1000));
        //Assert
        assertNull(result);
    }

    @Test
    void updateRoomSuccess() {
        // Arrange
        Room fakeExistingRoom = new Room(101, 2, BigDecimal.valueOf(500));
        Room fakeUpdatedRoom = new Room(104, 4, BigDecimal.valueOf(1000));

        UpdateRoomDto fakeDto = new UpdateRoomDto();
        fakeDto.setRoomNumber(104);
        fakeDto.setRoomSize(4);
        fakeDto.setRoomPrice(BigDecimal.valueOf(1000));

        Mockito.when(roomRepository.findById(1L))
                .thenReturn(Optional.of(fakeExistingRoom));

        Mockito.when(roomRepository.save(fakeExistingRoom))
                .thenReturn(fakeUpdatedRoom);

        // Act
        RoomResponseDto result = roomService.updateRoom(1L, fakeDto);

        // Assert
        assertEquals(fakeDto.getRoomNumber(), result.roomNumber());
        assertEquals(fakeDto.getRoomSize(), result.roomSize());
        assertEquals(fakeDto.getRoomPrice(), result.roomPrice());

        Mockito.verify(roomRepository).findById(1L);
        Mockito.verify(roomRepository).save(fakeExistingRoom);
    }

    @Test
    void updateRoomNotFound() {
        // Arrange
        UpdateRoomDto fakeDto = new UpdateRoomDto();
        fakeDto.setRoomNumber(104);
        fakeDto.setRoomSize(4);
        fakeDto.setRoomPrice(BigDecimal.valueOf(1000));

        Mockito.when(roomRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act
        RoomResponseDto result = roomService.updateRoom(1L, fakeDto);

        // Assert
        assertNull(result);

        Mockito.verify(roomRepository).findById(1L);
        Mockito.verify(roomRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void updateRoomSaveFails() {

        //Arrange
        Room fakeExistingRoom = new Room(101, 2, BigDecimal.valueOf(500));

        UpdateRoomDto fakeDto = new UpdateRoomDto();
        fakeDto.setRoomNumber(104);
        fakeDto.setRoomSize(4);
        fakeDto.setRoomPrice(BigDecimal.valueOf(1000));

        Mockito.when(roomRepository.findById(1L))
                .thenReturn(Optional.of(fakeExistingRoom));

        Mockito.when(roomRepository.save(fakeExistingRoom))
                .thenThrow(new RuntimeException("Something went wrong"));

        //Act
        RoomResponseDto result = roomService.updateRoom(1L, fakeDto);

        //Assert
        assertNull(result);

    }

    @Test
    void deleteRoomSuccess() {
        //Arrange
        Room fakeExistingRoom = new Room(101, 2, BigDecimal.valueOf(500));
        Mockito.when(roomRepository.findById(1L))
                .thenReturn(Optional.of(fakeExistingRoom));

        //Act
        boolean result = roomService.deleteRoom(1L);

        //Assert
        Mockito.verify(roomRepository).deleteById(1L);
        assertTrue(result);

    }

    @Test
    void deleteRoomNotFound() {
        //Arrange
        Room fakeExistingRoom = new Room(101, 2, BigDecimal.valueOf(500));
        Mockito.when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        boolean result = roomService.deleteRoom(1L);

        //Assert
        assertFalse(result);
    }

}