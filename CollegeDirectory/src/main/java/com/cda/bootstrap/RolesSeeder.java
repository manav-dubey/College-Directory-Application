package com.cda.bootstrap;

import com.cda.entities.Roles;
import com.cda.entities.RolesEnum;
import com.cda.repositries.RoleRepository;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RolesSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    public RolesSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RolesEnum[] roleNames = new RolesEnum[] { RolesEnum.STUDENT, RolesEnum.ADMINISTRATOR, RolesEnum.FACULTY_MEMBER };
        Map<RolesEnum, String> roleDescriptionMap = Map.of(
                RolesEnum.STUDENT, "Student user role",
                RolesEnum.ADMINISTRATOR, "Administrator role",
                RolesEnum.FACULTY_MEMBER, "Faculty role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Roles> optionalRole = roleRepository.findByRoleName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Roles roleToCreate = new Roles();

                roleToCreate.setRoleName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }
}