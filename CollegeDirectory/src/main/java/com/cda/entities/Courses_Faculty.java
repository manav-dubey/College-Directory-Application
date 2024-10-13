package com.cda.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "courses_faculty")
public class Courses_Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for each enrollment record

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private FacultyProfile faculty; // Foreign key linking to the FacultyProfile entity

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; // Foreign key linking to the Course entity
}

