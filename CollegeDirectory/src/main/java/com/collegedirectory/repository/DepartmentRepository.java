package com.collegedirectory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collegedirectory.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Custom query methods to retrieve departments
}

