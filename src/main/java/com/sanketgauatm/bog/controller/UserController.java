package com.sanketgauatm.bog.controller;

import com.sanketgauatm.bog.dto.UserDto;
import com.sanketgauatm.bog.model.User;
import com.sanketgauatm.bog.repo.user.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepo userRepo;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        Optional<List<UserDto>> users = userRepo.getAllUsers();
        if(users.isPresent()){
            return ResponseEntity.ok(users.get());
        }else{
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    }

    @PostMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        try{
            return ResponseEntity.ok(this.userRepo.save(user));
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    }
}
