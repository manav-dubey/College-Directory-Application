package com.cda.services;

import com.cda.entities.Course;
import com.cda.entities.Department;
import com.cda.entities.FacultyProfile;
import com.cda.entities.StudentProfile;

import java.util.List;

public interface StudentService  {
    StudentProfile getStudentProfileByUsername(String name);

    List<StudentProfile> getAllStudents();

    StudentProfile getStudentById(Long id);

    StudentProfile saveStudent(StudentProfile student,Long userId,List<Long> courseIds);

    void deleteStudent(Long id);

    List<Course> getAllCourses();

    List<Department> getAllDepartments();

    public List<FacultyProfile> getAdvisorsForStudent(Long studentId);

    public List<StudentProfile> searchStudents(String name, Long departmentId, String year);
}
