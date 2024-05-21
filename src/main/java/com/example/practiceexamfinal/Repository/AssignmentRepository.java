package com.example.practiceexamfinal.Repository;

import com.example.practiceexamfinal.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Assignment findByStoredFileName(String storedFileName);
}