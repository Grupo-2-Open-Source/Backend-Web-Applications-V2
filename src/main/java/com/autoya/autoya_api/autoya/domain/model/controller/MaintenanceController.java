package com.autoya.autoya_api.autoya.domain.model.controller;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Maintenance;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.MaintenanceRequest;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.MaintenanceResponse;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.MaintenanceRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.OwnerRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.TenantRepository;
import com.autoya.autoya_api.autoya.service.EmailService;
import com.autoya.autoya_api.autoya.service.MaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/maintenances")
public class MaintenanceController {


    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private MaintenanceService maintenanceService;

    @Operation(summary = "Muestra la lista de nombre de propiestarios que han alquilado algun vehiculo")
    @GetMapping("/owners/{id}")
    public ResponseEntity<List<String>> findOwnersWithMaintenance(@RequestParam Long tenantId) {
        // Busca al arrendatario (tenant) por su ID
        Optional<Tenant> tenantOptional = tenantRepository.findById(tenantId);

        if (tenantOptional.isPresent()) {
            Tenant tenant = tenantOptional.get();

            // Obtén la lista de nombres de propietarios a quienes el tenant ha alquilado vehículos
            List<String> ownerNames = tenant.getRentals()
                    .stream()
                    .map(rent -> rent.getOwner().getFullName())
                    .distinct()
                    .collect(Collectors.toList());

            return new ResponseEntity<>(ownerNames, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Muestra la lista de nombre de propiestarios que han alquilado algun vehiculo")
    @PostMapping("/send-maintenance-tenant")
    public ResponseEntity<String> sendMaintenanceReportEmail(@RequestBody MaintenanceRequest maintenanceRequest) {
        // Lógica para guardar la solicitud de mantenimiento en la base de datos
        MaintenanceRequest savedRequest = maintenanceService.createMaintenance(maintenanceRequest);

        // Obtiene la dirección de correo electrónico del propietario desde la base de datos
        Owner owner = ownerRepository.findById(savedRequest.getOwnerId()).orElse(null);
        if (owner == null) {
            return new ResponseEntity<>("Error: Propietario no encontrado.", HttpStatus.NOT_FOUND);
        }

        String ownerEmail = owner.getEmail();
        String subject = "Informe de mantenimiento";
        String text = "Tipo de problema: " + savedRequest.getProblemType() + "\n" +
                "Descripción: " + savedRequest.getDescription();
        try {
            emailService.sendEmail(ownerEmail, subject, text);

            return new ResponseEntity<>("Informe enviado exitosamente al propietario.", HttpStatus.OK);
        } catch (Exception e) {
            // Manejar excepciones en el envío de correo
            return new ResponseEntity<>("Informe enviado exitosamente al propietario 1", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
