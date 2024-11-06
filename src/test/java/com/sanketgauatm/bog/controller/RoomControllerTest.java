package com.sanketgauatm.bog.controller;

import com.sanketgauatm.bog.dto.RoomDto;
import com.sanketgauatm.bog.model.Status;
import com.sanketgauatm.bog.repo.room.RoomRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomControllerTest {

    private RoomRepo roomRepoMock;
    private RoomController roomControllerMock;

    @BeforeEach
    public void setUp() {
        roomRepoMock = mock(RoomRepo.class);
        roomControllerMock = new RoomController(roomRepoMock);
    }

    @Test
    void getAllRoomsShouldReturnAllRoomsWithStatusOk() {
        List<RoomDto> rooms = Arrays.asList(
                new RoomDto(1, "name1","Tartu" ,10, 1, Status.AVAILABLE),
                new RoomDto(2, "name2","Tallinn" ,20, 1, Status.AVAILABLE)
        );

        when(roomRepoMock.getAllRooms()).thenReturn(rooms);
        assertEquals(ResponseEntity.ok(rooms), roomControllerMock.getAllRooms());
    }
}