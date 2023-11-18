package com.autoya.autoya_api.iam.application.internal.commandservices;

import com.autoya.autoya_api.iam.domain.model.Entity.Role;
import com.autoya.autoya_api.iam.domain.model.commands.SeedRolesCommands;
import com.autoya.autoya_api.iam.domain.model.valueobjects.Roles;
import com.autoya.autoya_api.iam.domain.services.RoleCommandsService;
import com.autoya.autoya_api.iam.infraestructure.persistence.jpa.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleCommandServiceImpl implements RoleCommandsService {
    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    /**
     * @param command
     */
    @Override
    public void handle(SeedRolesCommands command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(Roles.valueOf(role.name())));
            }
        });

    }
}
