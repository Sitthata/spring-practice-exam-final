package com.example.practiceexamfinal.Repository;

import com.example.practiceexamfinal.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}