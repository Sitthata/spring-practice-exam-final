package com.example.practiceexamfinal.services;

import com.example.practiceexamfinal.Repository.StudentRepository;
import com.example.practiceexamfinal.model.Student;
import com.example.practiceexamfinal.model.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final ModelMapper modelMapper;

    public List<StudentDTO> findAll() {
       return repository.findAll()
               .stream()
               .map((element) -> modelMapper.map(element, StudentDTO.class))
               .collect(Collectors.toList());
    }

    public StudentDTO save(StudentDTO studentDTO) {
        Student studentToSave = modelMapper.map(studentDTO, Student.class);
        Student savedStudent = repository.save(studentToSave);
        return modelMapper.map(savedStudent, StudentDTO.class);
    }

    public StudentDTO findById(Long id) {
        Student student = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")
        );
        return modelMapper.map(student, StudentDTO.class);
    }

}
