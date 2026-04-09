package com.example.backend.controller;

import com.example.backend.model.Submission;
import com.example.backend.repository.SubmissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionRepository repo;

    // ✅ FILE UPLOAD
    @PostMapping("/upload")
    public Submission uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("studentName") String studentName,
            @RequestParam("assignmentTitle") String assignmentTitle,
            @RequestParam("course") String course
    ) throws IOException {

        // ✅ FIXED PATH (absolute path)
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;

        file.transferTo(new File(filePath));

        Submission s = new Submission();
        s.setStudentName(studentName);
        s.setAssignmentTitle(assignmentTitle);
        s.setCourse(course);
        s.setFileName(fileName);
        s.setStatus("submitted");

        return repo.save(s);
    }

    // ✅ GET ALL (Teacher)
    @GetMapping
    public List<Submission> getAll() {
        return repo.findAll();
    }

    // ✅ GET STUDENT SUBMISSIONS
    @GetMapping("/student/{email}")
    public List<Submission> getByStudent(@PathVariable String email) {
        return repo.findByStudentNameIgnoreCase(email);
    }

    // ✅ VIEW / DOWNLOAD PDF (FIXED)
    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        Path path = Paths.get(uploadDir).resolve(filename);

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(resource);
    }

    // ✅ GRADE SUBMISSION
    @PutMapping("/{id}")
    public Submission grade(@PathVariable Long id, @RequestBody Submission updated) {

        Submission s = repo.findById(id).orElseThrow();

        s.setMarks(updated.getMarks());
        s.setFeedback(updated.getFeedback());
        s.setStatus("graded");

        return repo.save(s);
    }
}