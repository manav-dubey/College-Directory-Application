package com.cda.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Title of the course

    private String description; // Description of the course content

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department; // Foreign key linking to the Department entity


}
