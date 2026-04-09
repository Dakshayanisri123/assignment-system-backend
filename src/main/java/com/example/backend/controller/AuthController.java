package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository repo;

    // ✅ SIGNUP
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return repo.save(user);
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public User login(@RequestBody User user) {

        User foundUser = repo.findByEmailIgnoreCaseAndPasswordAndRole(
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );

        return foundUser; // returns null if not found
    }
}