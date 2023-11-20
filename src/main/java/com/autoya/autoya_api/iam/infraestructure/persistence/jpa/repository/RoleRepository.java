package com.autoya.autoya_api.iam.infraestructure.persistence.jpa.repository;

import com.autoya.autoya_api.iam.domain.model.Entity.Role;
import com.autoya.autoya_api.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * This method is responsible for finding the roles by name.
     *
     * @param name the role name
     * @return the role object
     */
    Optional<Role> findByName(Roles name);

    /**
     * Check if a role with the given name exists.
     *
     * @param name the role name
     * @return true if the role exists, false otherwise
     */
    boolean existsByName(Roles name);
}
