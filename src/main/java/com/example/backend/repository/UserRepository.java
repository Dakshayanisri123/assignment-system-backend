package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // 🔥 FIX: ignore case for email
    User findByEmailIgnoreCaseAndPasswordAndRole(String email, String password, String role);
}