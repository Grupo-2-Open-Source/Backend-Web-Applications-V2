package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Notification;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByOwner_Id(Long ownerId);
    List<Notification> findByVehicleId(String vehicleId);
}
