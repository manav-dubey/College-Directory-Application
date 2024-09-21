package com.collegedirectory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collegedirectory.model.Course;
import com.collegedirectory.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable Long courseId) {
        return courseService.getCourseById(courseId);
    }

    @PostMapping("/{courseId}/enroll/{studentId}")
    public String enrollStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.enrollStudentInCourse(courseId, studentId);
        return "Student enrolled successfully";
    }

    @DeleteMapping("/{courseId}/unenroll/{studentId}")
    public String unenrollStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.unenrollStudentFromCourse(courseId, studentId);
        return "Student unenrolled successfully";
    }
}
