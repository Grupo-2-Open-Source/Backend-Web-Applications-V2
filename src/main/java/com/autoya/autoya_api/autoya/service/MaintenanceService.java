package com.autoya.autoya_api.autoya.service;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Maintenance;
import com.autoya.autoya_api.autoya.domain.model.events.requests.MaintenanceRequest;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.MaintenanceRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.OwnerRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing maintenance-related operations, such as creating maintenance requests.
 * This class is annotated with {@link Service} to indicate its role as a service component.
 */
@Service
public class MaintenanceService {
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private TenantRepository tenantRepository;

    public MaintenanceRequest createMaintenance(MaintenanceRequest maintenanceRequest) {
        Maintenance maintenance = new Maintenance();
        maintenance.setOwner(ownerRepository.findById(maintenanceRequest.getOwnerId()).orElse(null));
        maintenance.setTenant(tenantRepository.findById(maintenanceRequest.getTenantId()).orElse(null));
        maintenance.setProblemType(maintenanceRequest.getProblemType());
        maintenance.setDescription(maintenanceRequest.getDescription());
        maintenance.setImageUrl(maintenanceRequest.getImageUrl());

        maintenanceRepository.save(maintenance);
        MaintenanceRequest savedRequest = new MaintenanceRequest();
        savedRequest.setOwnerId(maintenance.getOwner().getId());
        savedRequest.setTenantId(maintenance.getTenant().getId());
        savedRequest.setProblemType(maintenance.getProblemType());
        savedRequest.setDescription(maintenance.getDescription());
        savedRequest.setImageUrl(maintenance.getImageUrl());

        return savedRequest;
    }
}
