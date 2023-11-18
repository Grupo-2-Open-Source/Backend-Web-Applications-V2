package com.autoya.autoya_api.iam.domain.model.commands;

import com.autoya.autoya_api.iam.domain.model.Entity.Role;

import java.util.List;

public record SingUpCommands(String username, String password, List<Role> roles) {
}
