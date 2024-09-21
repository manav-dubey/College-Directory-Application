package com.collegedirectory.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "department")
    private List<StudentProfile> students;

    @OneToMany(mappedBy = "department")
    private List<FacultyProfile> faculties;

    @OneToMany(mappedBy = "department")
    private List<AdministratorProfile> administrators;

    @OneToMany(mappedBy = "department")
    private List<Course> courses;

    // Getters and Setters
}

