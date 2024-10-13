package com.cda;

import com.cda.entities.Roles;
import com.cda.repositries.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollegeDirectoryApplication {


	public static void main(String[] args) {
		SpringApplication.run(CollegeDirectoryApplication.class, args);
	}
}
