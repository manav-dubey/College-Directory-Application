package com.cda.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_name",unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RolesEnum roleName;

    @Column(nullable = false)
    private String description;
}
