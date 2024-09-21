package com.collegedirectory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collegedirectory.model.FacultyProfile;
import com.collegedirectory.model.StudentProfile;
import com.collegedirectory.service.FacultyService;
import com.collegedirectory.service.StudentService;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/{facultyId}/students")
    public List<StudentProfile> getClassList(@PathVariable Long facultyId) {
        return facultyService.getStudentsForFaculty(facultyId);
    }

    @PutMapping("/{facultyId}/profile")
    public FacultyProfile updateProfile(@PathVariable Long facultyId, @RequestBody FacultyProfile updatedProfile) {
        return facultyService.updateFacultyProfile(facultyId, updatedProfile);
    }
}
