package com.cda.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admin_profiles")
public class AdministratorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private String photo; // URL or path to the administrator's profile photo
}
