package com.autoya.autoya_api.iam.domain.services;

import com.autoya.autoya_api.iam.domain.model.commands.SeedRolesCommands;

public interface RoleCommandsService {
    void handle(SeedRolesCommands command);
}
