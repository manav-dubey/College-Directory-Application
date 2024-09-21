package com.collegedirectory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collegedirectory.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Custom queries for courses by department, etc.
}

