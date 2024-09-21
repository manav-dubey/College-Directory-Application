package com.collegedirectory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegedirectory.model.FacultyProfile;
import com.collegedirectory.model.StudentProfile;
import com.collegedirectory.repository.FacultyProfileRepository;
import com.collegedirectory.repository.StudentProfileRepository;

@Service
public class FacultyService {

    @Autowired
    private FacultyProfileRepository facultyProfileRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    public List<StudentProfile> getStudentsForFaculty(Long facultyId) {
        return studentProfileRepository.findByFacultyId(facultyId);
    }

    public FacultyProfile updateFacultyProfile(Long facultyId, FacultyProfile updatedProfile) {
        FacultyProfile facultyProfile = facultyProfileRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        facultyProfile.setOfficeHours(updatedProfile.getOfficeHours());
        facultyProfile.setPhoto(updatedProfile.getPhoto());
        return facultyProfileRepository.save(facultyProfile);
    }

    public List<FacultyProfile> getAdvisorsForStudent(Long studentId) {
        // Logic to fetch advisors for a student
        return facultyProfileRepository.findAdvisorsByStudentId(studentId);
    }
}
