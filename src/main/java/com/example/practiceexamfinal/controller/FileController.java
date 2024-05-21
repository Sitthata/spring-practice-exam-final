package com.example.practiceexamfinal.controller;

import com.example.practiceexamfinal.properties.FileStorageProperties;
import com.example.practiceexamfinal.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileStorageProperties fileStorageProperties;
    private final FileService fileService;

    @PostMapping("")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file) {
        fileService.storeFile(file);
        return ResponseEntity.ok(String.format("File %s uploaded successfully", file.getOriginalFilename()));
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Object> serveFile(@PathVariable String filename) {
        Resource file = fileService.loadFileAsResource(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }
}
