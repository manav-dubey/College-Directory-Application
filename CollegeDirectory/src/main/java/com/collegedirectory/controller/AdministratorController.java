package com.collegedirectory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collegedirectory.model.FacultyProfile;
import com.collegedirectory.model.StudentProfile;
import com.collegedirectory.service.AdministratorService;

@RestController
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdministratorService adminService;

    @GetMapping("/students")
    public List<StudentProfile> getAllStudents() {
        return adminService.getAllStudents();
    }

    @PostMapping("/students")
    public StudentProfile addStudent(@RequestBody StudentProfile studentProfile) {
        return adminService.addStudent(studentProfile);
    }

    @PutMapping("/students/{studentId}")
    public StudentProfile updateStudent(@PathVariable Long studentId, @RequestBody StudentProfile updatedProfile) {
        return adminService.updateStudent(studentId, updatedProfile);
    }

    @DeleteMapping("/students/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        adminService.deleteStudent(studentId);
    }

    @GetMapping("/faculty")
    public List<FacultyProfile> getAllFaculty() {
        return adminService.getAllFaculty();
    }

    @PostMapping("/faculty")
    public FacultyProfile addFaculty(@RequestBody FacultyProfile facultyProfile) {
        return adminService.addFaculty(facultyProfile);
    }

    @PutMapping("/faculty/{facultyId}")
    public FacultyProfile updateFaculty(@PathVariable Long facultyId, @RequestBody FacultyProfile updatedProfile) {
        return adminService.updateFaculty(facultyId, updatedProfile);
    }

    @DeleteMapping("/faculty/{facultyId}")
    public void deleteFaculty(@PathVariable Long facultyId) {
        adminService.deleteFaculty(facultyId);
    }

    @GetMapping("/dashboard")
    public Object getDashboardData() {
        return adminService.getDashboardData();
    }
}
