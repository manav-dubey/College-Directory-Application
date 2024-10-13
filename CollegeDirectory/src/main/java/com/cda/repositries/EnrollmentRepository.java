package com.cda.repositries;

import com.cda.entities.Enrollment;
import com.cda.entities.StudentProfile;
import com.cda.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    public void deleteByStudentId(Long studentId);

    List<Enrollment> findByStudentId(Long studentId);

//    @Query("SELECT s FROM StudentProfile s " +
//            "JOIN Enrollment e ON s.id = e.student.id " +
//            "JOIN Courses_Faculty cf ON e.course.id = cf.course.id " +
//            "WHERE cf.faculty.id = :facultyId")
//    List<StudentProfile> findStudentsByFacultyId(@Param("facultyId") Long facultyId);

    @Query("SELECT u FROM Users u " +
            "JOIN StudentProfile sp ON u.id = sp.user.id " +
            "JOIN Enrollment e ON sp.id = e.student.id " +
            "JOIN Course c ON e.course.id = c.id " +
            "JOIN Courses_Faculty cf ON c.id = cf.course.id " +
            "JOIN FacultyProfile fp ON cf.faculty.id = fp.id "+
            "WHERE fp.user.id = :userId")
    List<Users> findStudentsByFacultyId(@Param("userId") Long facultyId);
}
