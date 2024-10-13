package com.cda.services.impl;

import com.cda.entities.Roles;
import com.cda.repositries.RoleRepository;
import com.cda.services.RolesServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServicesImpl implements RolesServices {

    private RoleRepository roleRepository;

    public RolesServicesImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }
}
