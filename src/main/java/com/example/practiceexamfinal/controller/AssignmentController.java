package com.example.practiceexamfinal.controller;

import com.example.practiceexamfinal.model.dto.AssignmentDTO;
import com.example.practiceexamfinal.services.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/assignment")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    @GetMapping("")
    public ResponseEntity<List<AssignmentDTO>> listAssignments() {
        return ResponseEntity.ok((assignmentService.getAllAssignments()));
    }

    @PostMapping("")
    public ResponseEntity<Object> uploadAssignment(@RequestParam("file") MultipartFile file) {
        assignmentService.storeAssignment(file);
        return ResponseEntity.ok(String.format("File %s uploaded successfully", file.getOriginalFilename()));

    }

    @GetMapping("/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        Resource file = assignmentService.loadFileAsResource(fileName);
        return ResponseEntity.ok().body(file);
    }
}
