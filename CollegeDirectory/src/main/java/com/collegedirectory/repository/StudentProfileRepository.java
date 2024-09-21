package com.collegedirectory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collegedirectory.model.StudentProfile;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
	 List<StudentProfile> findByNameContaining(String name);
	 List<StudentProfile> findByFacultyId(Long facultyId);
	 StudentProfile findById(Long StudentId); 
}

