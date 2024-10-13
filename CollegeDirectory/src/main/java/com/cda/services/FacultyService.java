package com.cda.services;

import com.cda.entities.*;

import java.util.List;
import java.util.Map;

public interface FacultyService {

    FacultyProfile getFacultyProfileByUsername(String name);

    List<FacultyProfile> getAllFaculty();

    FacultyProfile getFacultyById(Long id);

    FacultyProfile getFacultyByUserId(Long id);

    FacultyProfile saveFaculty(FacultyProfile student,Long userId,List<Long> courseIds);


    void deleteFaculty(Long id);

    List<Department> getAllDepartments();

    List<Course> getAllCourses();

     List<Users> getStudentsForFaculty(Long facultyId);

     FacultyProfile updateFacultyWithUsers(FacultyProfile faculty,Long userId, List<Long> courseIds);

}
