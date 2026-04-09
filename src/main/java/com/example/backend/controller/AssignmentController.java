package com.example.backend.controller;

import com.example.backend.model.Assignment;
import com.example.backend.repository.AssignmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentRepository repo;

    // ✅ CREATE
    @PostMapping
    public Assignment create(@RequestBody Assignment a) {
        return repo.save(a);
    }

    // ✅ GET ALL
    @GetMapping
    public List<Assignment> getAll() {
        return repo.findAll();
    }
}