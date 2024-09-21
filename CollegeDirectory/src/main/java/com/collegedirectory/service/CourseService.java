package com.collegedirectory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegedirectory.model.Course;
import com.collegedirectory.model.StudentProfile;
import com.collegedirectory.repository.CourseRepository;
import com.collegedirectory.repository.EnrollmentRepository;
import com.collegedirectory.repository.StudentProfileRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
    private StudentProfileRepository studentProfileRepository;
    public void enrollStudentInCourse(Long courseId, Long studentId) {
        // Fetch the student and course objects from the repository
        StudentProfile student = studentProfileRepository.findById(studentId);
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        
        // Create an Enrollment object
        Enrollment enrollment = new Enrollment(null, student, course);

        // Save the enrollment
        enrollmentRepository.save(enrollment);
    }


    public void unenrollStudentFromCourse(Long courseId, Long studentId) {
        // Logic to unenroll a student from a course
        enrollmentRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }
}
