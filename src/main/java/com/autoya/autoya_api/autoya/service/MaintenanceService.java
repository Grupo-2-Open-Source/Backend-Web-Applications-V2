package com.autoya.autoya_api.autoya.service;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Maintenance;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.MaintenanceRequest;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.MaintenanceRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.OwnerRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.TenantRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MaintenanceService {
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private TenantRepository tenantRepository;

    public MaintenanceRequest createMaintenance(MaintenanceRequest maintenanceRequest) {
        // Aquí puedes realizar validaciones o lógica adicional antes de guardar la solicitud en la base de datos

        // Crear una instancia de Maintenance a partir de los datos de MaintenanceRequest
        Maintenance maintenance = new Maintenance();
        maintenance.setOwner(ownerRepository.findById(maintenanceRequest.getOwnerId()).orElse(null));
        maintenance.setTenant(tenantRepository.findById(maintenanceRequest.getTenantId()).orElse(null));
        maintenance.setProblemType(maintenanceRequest.getProblemType());
        maintenance.setDescription(maintenanceRequest.getDescription());
        maintenance.setImageUrl(maintenanceRequest.getImageUrl());
        // Otros campos...

        // Guarda la instancia de Maintenance en la base de datos
        maintenanceRepository.save(maintenance);

        // Puedes devolver una instancia de MaintenanceRequest a partir de la información de Maintenance
        MaintenanceRequest savedRequest = new MaintenanceRequest();
        savedRequest.setOwnerId(maintenance.getOwner().getId());
        savedRequest.setTenantId(maintenance.getTenant().getId());
        savedRequest.setProblemType(maintenance.getProblemType());
        savedRequest.setDescription(maintenance.getDescription());
        savedRequest.setImageUrl(maintenance.getImageUrl());
        // Otros campos...

        return savedRequest;
    }
}
