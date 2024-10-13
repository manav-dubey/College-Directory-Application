package com.cda.services.impl;

import com.cda.entities.AdministratorProfile;
import com.cda.entities.FacultyProfile;
import com.cda.entities.StudentProfile;
import com.cda.entities.Users;
import com.cda.payload.UserDto;
import com.cda.repositries.AdministratorProfileRepository;
import com.cda.repositries.FacultyProfileRepository;
import com.cda.repositries.StudentRepository;
import com.cda.repositries.UserRepository;
import com.cda.services.UserServices;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServicesImpl implements UserServices {

    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentProfileRepository;
    @Autowired
    private AdministratorProfileRepository adminProfileRepository;
    @Autowired
    private FacultyProfileRepository facultyProfileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper mapper;

    public UserServicesImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDto saveUser(UserDto dto) {
        Users user = mapToEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        Users saved = userRepository.save(user);

        // Save profile based on role
        switch (saved.getRole().getRoleName()) { // No need to convert to lowercase anymore
            case STUDENT:
                System.out.println("Saving studentProfile... switch case - " + saved.getRole().getRoleName());
                StudentProfile studentProfile = new StudentProfile();
                studentProfile.setUser(saved);
                studentProfileRepository.save(studentProfile);
                break;
            case ADMINISTRATOR:
                System.out.println("Saving administratorProfile... switch case - " + saved.getRole().getRoleName());
                AdministratorProfile adminProfile = new AdministratorProfile();
                adminProfile.setUser(saved);
                adminProfileRepository.save(adminProfile);
                break;
            case FACULTY_MEMBER:
                System.out.println("Saving facultyProfile... switch case - " + saved.getRole().getRoleName());
                FacultyProfile facultyProfile = new FacultyProfile();
                facultyProfile.setUser(saved);
                facultyProfileRepository.save(facultyProfile);
                break;
            default:
                throw new IllegalArgumentException("Invalid role");
        }

        return mapToDTO(saved);
    }

    private UserDto mapToDTO(Users user) {
        return mapper.map(user, UserDto.class);
    }

    private Users mapToEntity(UserDto dto) {
        return mapper.map(dto, Users.class);
    }
}
