package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.backend.model.Assignment;
import com.example.backend.repository.AssignmentRepository;

import java.util.List;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public org.springframework.boot.CommandLineRunner init(AssignmentRepository repo) {
        return args -> {

            if (repo.count() == 0) {

                Assignment a1 = new Assignment();
                a1.setTitle("React Basics");
                a1.setCourse("Web Development");
                a1.setDeadline("2026-03-10");

                Assignment a2 = new Assignment();
                a2.setTitle("OOP Concepts");
                a2.setCourse("Java Programming");
                a2.setDeadline("2026-03-15");

                Assignment a3 = new Assignment();
                a3.setTitle("Database Design");
                a3.setCourse("DBMS");
                a3.setDeadline("2026-03-20");

                repo.saveAll(List.of(a1, a2, a3));
            }
        };
    }
}