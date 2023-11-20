package com.autoya.autoya_api.iam.domain.services;

import com.autoya.autoya_api.iam.domain.model.aggregates.User;
import com.autoya.autoya_api.iam.domain.model.queries.GetAllUsersQueries;
import com.autoya.autoya_api.iam.domain.model.queries.GetUserByIdQueries;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQueries query);

    Optional<User> handle(GetUserByIdQueries query);
}
