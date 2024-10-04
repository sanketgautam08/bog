package com.sanketgauatm.bog.controller;

import com.sanketgauatm.bog.model.Conference;
import com.sanketgauatm.bog.dto.ConferenceDto;
import com.sanketgauatm.bog.model.User;
import com.sanketgauatm.bog.repo.room.RoomRepo;
import com.sanketgauatm.bog.repo.conference.ConferenceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conferences")
public class ConferenceController {

    private final Logger LOGGER = LoggerFactory.getLogger(ConferenceController.class);
    private final ConferenceRepo conferenceRepo;
    private final RoomRepo roomRepo;

    public ConferenceController(ConferenceRepo conferenceRepo, RoomRepo roomRepo) {
        this.conferenceRepo = conferenceRepo;
        this.roomRepo = roomRepo;
    }

    @GetMapping("/")
    public ResponseEntity<List<ConferenceDto>> getAllConferences() {
       try{
           return new ResponseEntity<>(conferenceRepo.getAllConferences(), HttpStatusCode.valueOf(200));
       }catch (Exception e){
           LOGGER.error("Error fetching all conferences.\n{}",e.getMessage());
           return new ResponseEntity<>(null,HttpStatusCode.valueOf(400));
       }
    }

    @GetMapping("/available/{id}")
    public ResponseEntity<Boolean> isConferenceAvailable(@PathVariable int id) {
        Optional<Conference> conference = conferenceRepo.findById(id);
        if(conference.isPresent()) {
            int registeredUsers = conferenceRepo.countRegisteredUsers(id);
            int roomCapacity = roomRepo.getRoomCapacity(conference.get().getRoom().getId());
            return ResponseEntity.ok(roomCapacity > registeredUsers);
        }
        return ResponseEntity.ok(false);
    }

    @PostMapping("/")
    public ResponseEntity<Conference> createConference(@RequestBody Conference conference) {
        Conference available = conferenceRepo.findByRoomAndDateTime(conference.getRoom(), conference.getDateTime());
        if (available == null) {
            return new ResponseEntity<>(conferenceRepo.save(conference), HttpStatusCode.valueOf(201));
        }else {
            LOGGER.error("Conference already exists in the selected room for the chosen date.");
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    }

    @DeleteMapping("/{conferenceId}")
    public ResponseEntity<String> deleteConference(@PathVariable int conferenceId) {
        boolean conferenceDeleted = conferenceRepo.deleteConference(conferenceId);
        if(conferenceDeleted) {
            return ResponseEntity.ok("Conference deleted");
        }else {
            LOGGER.error("Error deleting conference:: {}", conferenceId);
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    }

    @PostMapping("/add-user/{id}")
    public ResponseEntity<String> registerUserToConference(@PathVariable int id, @RequestBody User user) {
        Optional<Conference> conference = conferenceRepo.findById(id);
        if(conference.isEmpty()){
            LOGGER.error("Couldn't add user to conference");
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
        int roomId = conference.get().getRoom().getId();
        Integer roomCapacity =  roomRepo.getRoomCapacity(roomId);
        Integer registeredUsers = conferenceRepo.countRegisteredUsers(id);

        if(user.getId() != null && (roomCapacity > registeredUsers)) {
            String uniqueConferenceCode = conferenceRepo.insertIntoConferenceUsers(id,user);
            LOGGER.info("user added to conference");
            return ResponseEntity.ok(uniqueConferenceCode);
        }else{
            LOGGER.error("Couldn't add user to conference");
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateConferenceDetails(@PathVariable int id, @RequestBody Conference conference) {
        Optional<Conference> conferenceDb = conferenceRepo.findById(id);
        boolean updated = false;
        if(conferenceDb.isPresent()) {
            conference.setId(conferenceDb.get().getId());
            updated = conferenceRepo.updateConferenceDetails(conference);
        }
        return updated ? new ResponseEntity<>("Updated", HttpStatusCode.valueOf(200))
            : new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
    }

}

