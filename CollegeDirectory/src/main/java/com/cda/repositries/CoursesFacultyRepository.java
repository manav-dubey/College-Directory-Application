package com.cda.repositries;

import com.cda.entities.Courses_Faculty;
import com.cda.entities.FacultyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoursesFacultyRepository extends JpaRepository<Courses_Faculty ,Long> {
    public void deleteByFacultyId(Long id);

    @Query("SELECT cf.faculty.id FROM Courses_Faculty cf WHERE cf.course.id = :courseId")
    List<Long> findFacultyIdsByCourseId(@Param("courseId") Long courseId);

    // Define a query to find all FacultyCourses by facultyId
    List<Courses_Faculty> findByFacultyId(Long facultyId);
}
