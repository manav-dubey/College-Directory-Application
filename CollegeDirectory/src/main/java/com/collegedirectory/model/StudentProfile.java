package com.collegedirectory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class StudentProfile {
    @Id
    private Long userId;

    private String photo;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private String year;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
}
