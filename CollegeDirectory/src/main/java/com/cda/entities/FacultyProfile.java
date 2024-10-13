package com.cda.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "faculty_profiles")
public class FacultyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    private String photo; // URL or path to the faculty member's profile photo

    private String officeHours;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Courses_Faculty> courses_faculties;

    public String getName() {
        return user != null ? user.getName() : null;
    }

    public String getPhone() {
        return user != null ? user.getPhone() : null;
    }

    public String getEmail() {
        return user != null ? user.getEmail() : null;
    }
}
