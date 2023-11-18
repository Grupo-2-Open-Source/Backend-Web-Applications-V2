package com.autoya.autoya_api.iam.infraestructure.persistence.jpa.repository;

import com.autoya.autoya_api.iam.domain.model.aggregates.User;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;

@Repository
public interface UserRepository  {
    Optional<User> findByUsername(String username);
    /**
     *
     */
    boolean existsByUsername (String username);
}
