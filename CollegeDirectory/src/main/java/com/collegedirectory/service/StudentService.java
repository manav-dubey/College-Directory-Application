package com.collegedirectory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegedirectory.model.StudentProfile;
import com.collegedirectory.repository.StudentProfileRepository;

@Service
public class StudentService {

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    public StudentProfile getStudentProfile(Long id) {
        return studentProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<StudentProfile> searchStudentsByName(String name) {
        return studentProfileRepository.findByNameContaining(name);
    }
}
