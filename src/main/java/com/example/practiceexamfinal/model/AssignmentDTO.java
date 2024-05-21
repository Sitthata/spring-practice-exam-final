package com.example.practiceexamfinal.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Assignment}
 */
@Data
@Builder
public class AssignmentDTO {
    Long id;
    String originalFileName;
    String fileType;
    String filePath;

    public static AssignmentDTO from(Assignment assignment) {
        return AssignmentDTO.builder()
                .id(assignment.getId())
                .originalFileName(assignment.getOriginalFileName())
                .fileType(assignment.getFileType())
                .filePath(assignment.getFilePath())
                .build();
    }
}