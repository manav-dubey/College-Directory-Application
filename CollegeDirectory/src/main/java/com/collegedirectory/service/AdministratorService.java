package com.collegedirectory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegedirectory.model.FacultyProfile;
import com.collegedirectory.model.StudentProfile;
import com.collegedirectory.repository.FacultyProfileRepository;
import com.collegedirectory.repository.StudentProfileRepository;

@Service
public class AdministratorService {

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private FacultyProfileRepository facultyProfileRepository;

    public List<StudentProfile> getAllStudents() {
        return studentProfileRepository.findAll();
    }

    public StudentProfile addStudent(StudentProfile studentProfile) {
        return studentProfileRepository.save(studentProfile);
    }

    public StudentProfile updateStudent(Long studentId, StudentProfile updatedProfile) {
        StudentProfile studentProfile = studentProfileRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        studentProfile.setPhoto(updatedProfile.getPhoto());
        studentProfile.setYear(updatedProfile.getYear());
        return studentProfileRepository.save(studentProfile);
    }

    public void deleteStudent(Long studentId) {
        studentProfileRepository.deleteById(studentId);
    }

    public List<FacultyProfile> getAllFaculty() {
        return facultyProfileRepository.findAll();
    }

    public FacultyProfile addFaculty(FacultyProfile facultyProfile) {
        return facultyProfileRepository.save(facultyProfile);
    }

    public FacultyProfile updateFaculty(Long facultyId, FacultyProfile updatedProfile) {
        FacultyProfile facultyProfile = facultyProfileRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        facultyProfile.setOfficeHours(updatedProfile.getOfficeHours());
        facultyProfile.setPhoto(updatedProfile.getPhoto());
        return facultyProfileRepository.save(facultyProfile);
    }

    public void deleteFaculty(Long facultyId) {
        facultyProfileRepository.deleteById(facultyId);
    }

    public Object getDashboardData() {
        // Logic to fetch and aggregate data for the dashboard
        return new Object();
    }
}
