package com.collegedirectory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collegedirectory.model.FacultyProfile;

@Repository
public interface FacultyProfileRepository extends JpaRepository<FacultyProfile, Long> {
	List<FacultyProfile> findAdvisorsByStudentId(Long studentId);
}
