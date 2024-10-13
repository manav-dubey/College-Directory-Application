package com.cda.repositries;

import com.cda.entities.FacultyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;
import java.util.Optional;

public interface FacultyProfileRepository extends JpaRepository<FacultyProfile, Long> {

    Optional<FacultyProfile> findByUser_Email(String username);

    Optional<FacultyProfile> findByUserId(Long userId);

    }
