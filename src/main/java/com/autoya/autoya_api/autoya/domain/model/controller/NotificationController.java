package com.autoya.autoya_api.autoya.domain.model.controller;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Notification;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.entities.Vehicule;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.NotificationResponse;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.NotificationRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.OwnerRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Operation(summary = "Devuelve lista de datos de solicitud de alquiler por el owner")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByOwnerId(@PathVariable Long ownerId) {
        List<Notification> notifications = notificationRepository.findByOwner_Id(ownerId);
        List<NotificationResponse> responseList = new ArrayList<>();

        for (Notification notification : notifications) {
            Tenant tenant = notification.getTenant();
            Vehicule vehicle = notification.getVehicle();

            NotificationResponse response = new NotificationResponse();
            response.setTenantName(tenant.getFullName());
            response.setVehicleBrand(vehicle.getBrand());
            response.setVehicleModel(vehicle.getModel());

            responseList.add(response);
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
