package com.autoya.autoya_api.iam.domain.services;

import com.autoya.autoya_api.iam.domain.model.Entity.Role;
import com.autoya.autoya_api.iam.domain.model.queries.GetAllRolesQueries;
import com.autoya.autoya_api.iam.domain.model.queries.GetRoleByNameQueries;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQueries query);

    Optional<Role> handle(GetRoleByNameQueries   query);
}
