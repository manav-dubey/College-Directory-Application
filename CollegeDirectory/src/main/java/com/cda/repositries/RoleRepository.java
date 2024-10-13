package com.cda.repositries;

import com.cda.entities.Roles;
import com.cda.entities.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles,Long> {

    Optional<Roles> findByRoleName(RolesEnum name);
    @Override
    List<Roles> findAll();
}
