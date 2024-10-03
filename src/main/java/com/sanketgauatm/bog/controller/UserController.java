package com.sanketgauatm.bog.controller;

import com.sanketgauatm.bog.model.User;
import com.sanketgauatm.bog.repo.user.UserRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @GetMapping("/")
    public List<User> getAllUsers(){
        return this.userRepo.findAll();
    }

    @PostMapping("/")
    public User saveUser(@RequestBody User user){
        return this.userRepo.save(user);
    }
}
