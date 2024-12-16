package com.cda.repositries;

import com.cda.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {

    List<Course> findByDepartmentId(Long departmentId);
}
