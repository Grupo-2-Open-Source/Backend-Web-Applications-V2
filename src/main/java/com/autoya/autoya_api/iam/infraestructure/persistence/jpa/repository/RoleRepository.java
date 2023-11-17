package com.autoya.autoya_api.iam.infraestructure.persistence.jpa.repository;

import com.autoya.autoya_api.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;

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
