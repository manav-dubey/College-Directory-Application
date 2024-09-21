package com.collegedirectory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collegedirectory.model.AdministratorProfile;

@Repository
public interface AdministratorProfileRepository extends JpaRepository<AdministratorProfile, Long> {
    // Custom queries for admin management
}
