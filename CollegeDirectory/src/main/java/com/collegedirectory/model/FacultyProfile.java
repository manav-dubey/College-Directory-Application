package com.collegedirectory.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class FacultyProfile {
    @Id
    private Long userId;

    private String photo;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private String officeHours;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "faculty")
    private List<Course> courses;

    // Getters and Setters
}
