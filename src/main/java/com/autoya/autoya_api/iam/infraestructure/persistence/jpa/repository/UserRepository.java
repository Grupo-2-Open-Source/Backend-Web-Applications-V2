package com.autoya.autoya_api.iam.infraestructure.persistence.jpa.repository;

import com.autoya.autoya_api.iam.domain.model.aggregates.User;
import com.autoya.autoya_api.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;

/**
 * JPA Repository interface for managing persistence operations related to users in the IAM (Identity and Access Management) domain.
 * This interface is responsible for handling user entities.
 * It declares methods for querying user information, such as finding a user by username and checking the existence of a user by username.
 */
@Repository
public interface UserRepository  {
    Optional<User> findByUsername(String username);
    /**
     *
     */
    boolean existsByUsername (String username);
}
