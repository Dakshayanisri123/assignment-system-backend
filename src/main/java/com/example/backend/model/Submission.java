package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;
    private String assignmentTitle;
    private String course;

    private String fileName;

    @Column(length = 500)
    private String filePath;   // ✅ better for long paths

    private String status;     // PENDING / GRADED
    private String marks;
    private String feedback;
}