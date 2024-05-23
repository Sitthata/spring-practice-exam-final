package com.example.practiceexamfinal.services;

import com.example.practiceexamfinal.Repository.AssignmentRepository;
import com.example.practiceexamfinal.model.Assignment;
import com.example.practiceexamfinal.model.dto.AssignmentDTO;
import com.example.practiceexamfinal.properties.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class AssignmentService {
    private final Path fileStorageLocation;
    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(FileStorageProperties fileStorageProperties, AssignmentRepository assignmentRepository) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath()
                .normalize();
        this.assignmentRepository = assignmentRepository;

        if (!Files.exists(this.fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
            }
        }
    }

    public List<AssignmentDTO> getAllAssignments() {
        return assignmentRepository.findAll()
                .stream()
                .map(AssignmentDTO::from)
                .toList();
    }

    public AssignmentDTO storeAssignment(MultipartFile file) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uniqueFilename = UUID.randomUUID() + fileExtension;

        try {
            if (originalFilename.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + originalFilename);
            }

            Path targetLocation = this.fileStorageLocation.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation);

            Assignment assignment = Assignment.builder()
                    .originalFileName(originalFilename)
                    .storedFileName(uniqueFilename)
                    .fileType(fileExtension)
                    .filePath(targetLocation.toString())
                    .build();
            return AssignmentDTO.from(assignmentRepository.save(assignment));
        } catch (Exception ex) {
            throw new RuntimeException("Could not store file " + originalFilename + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found");
            }
            return resource;
        } catch (Exception ex) {
            throw new RuntimeException("File not found", ex);
        }
    }
    public void deleteFile(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found " + filename);
            }
            Files.deleteIfExists(filePath);
            assignmentRepository.deleteById(assignmentRepository.findByStoredFileName(filename).getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
