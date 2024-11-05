package com.sanketgauatm.bog.controller;

import com.sanketgauatm.bog.dto.RoomDto;
import com.sanketgauatm.bog.model.Room;
import com.sanketgauatm.bog.model.Status;
import com.sanketgauatm.bog.repo.room.RoomRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bog/rooms")
public class RoomController {

    private final RoomRepo roomRepo;
    private final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @GetMapping("/")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        try{
            return new ResponseEntity<>(roomRepo.getAllRooms(), HttpStatusCode.valueOf(200));
        }catch(Exception e){
            LOGGER.error("Error while fetching rooms\n {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    }

    @GetMapping("/get-status/{id}")
    public ResponseEntity<Status> getRoomById(@PathVariable int id) {
        return roomRepo.getRoomStatus(id).isPresent()
                ? ResponseEntity.ok(roomRepo.getRoomStatus(id).get())
                :  new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
    }

    @PostMapping("/")
    public ResponseEntity<RoomDto> createRoom(@RequestBody Room room) {
        Optional<RoomDto> roomCreated = roomRepo.createRoom(room);
        return roomCreated.map(roomDto -> new ResponseEntity<>(roomDto, HttpStatusCode.valueOf(201)))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatusCode.valueOf(400)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@RequestBody Room room, @PathVariable int id) {
        Optional<Room> roomFromDb = roomRepo.findById(id);
        if(roomFromDb.isEmpty()){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
        room.setCreatedBy(roomFromDb.get().getCreatedBy());
        room.setId(id);
        boolean isValid = roomRepo.validateCapacityAndStatus(room);
        if(!isValid){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
        try{
            Room updatedRoom = roomRepo.save(room);
            LOGGER.info("Room updated: {}", updatedRoom);
            RoomDto roomDto = new RoomDto(
                    updatedRoom.getId(),
                    updatedRoom.getName(),
                    updatedRoom.getLocation(),
                    updatedRoom.getMaxCapacity(),
                    updatedRoom.getCreatedBy().getId(),
                    updatedRoom.getStatus())
                    ;
            return new ResponseEntity<>(roomDto, HttpStatusCode.valueOf(200));
        }catch (Exception e){
            LOGGER.error("Error while updating room to db.\n {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    }
}
