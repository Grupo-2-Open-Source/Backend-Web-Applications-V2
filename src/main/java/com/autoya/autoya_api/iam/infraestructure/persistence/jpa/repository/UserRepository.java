package com.autoya.autoya_api.iam.infraestructure.persistence.jpa.repository;

import com.autoya.autoya_api.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository interface for managing persistence operations related to users in the IAM (Identity and Access Management) domain.
 * This interface is responsible for handling user entities.
 * It declares methods for querying user information, such as finding a user by username and checking the existence of a user by username.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    /**
     *
     */
    boolean existsByUsername (String username);
}
