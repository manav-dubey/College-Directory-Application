package com.collegedirectory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegedirectory.model.User;
import com.collegedirectory.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User authenticate(String username, String password, String role) {
        return userRepository.findByUsernameAndPasswordAndRole(username, password, role);
    }
}
