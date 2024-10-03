package com.sanketgauatm.bog.controller;

import com.sanketgauatm.bog.dto.RoomDto;
import com.sanketgauatm.bog.model.Room;
import com.sanketgauatm.bog.repo.room.RoomRepo;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepo roomRepo;

    public RoomController(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @GetMapping("/")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<Room> rooms = roomRepo.findAll();
        List<RoomDto> roomDtos = new ArrayList<>();
        for(Room room : rooms) {
            roomDtos.add(new RoomDto(room.getId(), room.getName(),room.getLocation(),room.getMaxCapacity()));
        }
        return new ResponseEntity<>(roomDtos, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        try{
            return new ResponseEntity<>(roomRepo.save(room), HttpStatusCode.valueOf(201));
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@RequestBody Room room, @PathVariable int id) {
        room.setId(id);
        boolean isValid = roomRepo.validateCapacityAndStatus(room);
        if(!isValid){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
        Room updatedRoom = roomRepo.save(room);
        return new ResponseEntity<>(updatedRoom, HttpStatusCode.valueOf(200));
    }
}
