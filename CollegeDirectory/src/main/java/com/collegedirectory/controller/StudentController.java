package com.collegedirectory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collegedirectory.model.FacultyProfile;
import com.collegedirectory.model.StudentProfile;
import com.collegedirectory.service.FacultyService;
import com.collegedirectory.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/{id}/profile")
    public StudentProfile getStudentProfile(@PathVariable Long id) {
        return studentService.getStudentProfile(id);
    }

    @GetMapping("/search")
    public List<StudentProfile> searchStudents(@RequestParam String name) {
        return studentService.searchStudentsByName(name);
    }

    @GetMapping("/{id}/advisors")
    public List<FacultyProfile> getFacultyAdvisors(@PathVariable Long id) {
        return facultyService.getAdvisorsForStudent(id);
    }
}
