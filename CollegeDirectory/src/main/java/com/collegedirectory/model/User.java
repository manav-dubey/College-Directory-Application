package com.collegedirectory.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class User {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(unique = true, nullable = false)
	    private String username;

	    @Column(nullable = false)
	    private String password;

	    @Enumerated(EnumType.STRING)
	    private Role role;

	    private String name;
	    private String email;
	    private String phone;	

	    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	    private StudentProfile studentProfile;

	    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	    private FacultyProfile facultyProfile;

	    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	    private AdministratorProfile administratorProfile;

		

	    
	}



