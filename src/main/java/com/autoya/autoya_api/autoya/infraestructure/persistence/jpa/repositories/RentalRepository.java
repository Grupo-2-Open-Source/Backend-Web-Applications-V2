package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Rent;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository interface for managing persistence operations related to rentals.
 * Extends JpaRepository to inherit common CRUD operations.
 */
@Repository
public interface RentalRepository extends JpaRepository<Rent, Long> {
    // Puedes agregar métodos de consulta personalizados si es necesario
    List<Rent> findByTenant_Id(Long tenantId);

    List<Rent> findByVehicleId(String vehicleId);
}
