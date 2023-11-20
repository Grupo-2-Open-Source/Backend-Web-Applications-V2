package com.autoya.autoya_api.iam.infraestructure.persistence.jpa.repository;

import com.autoya.autoya_api.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;

/**
 * JPA Repository interface for managing persistence operations related to roles in the IAM (Identity and Access Management) domain.
 * This interface is responsible for handling role entities.
 * It declares methods for querying role information, such as finding roles by name and checking the existence of a role by name.
 */
@Repository
public interface RoleRepository  {
    /**
     * This method is responsible for finding  the roles by name
     * @param name the role name
     * @return the role objetc
     */
    Optional<Role>  findByName(Roles name);
    /**
     *
     */
    boolean existsByName (Roles name);
}
