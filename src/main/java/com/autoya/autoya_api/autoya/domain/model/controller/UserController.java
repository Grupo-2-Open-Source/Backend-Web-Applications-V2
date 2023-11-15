package com.autoya.autoya_api.autoya.domain.model.controller;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.LoginRequest;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.LoginResponse;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.RegisterRequest;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.OwnerRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.TenantRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private TenantRepository tenantRepository;


    @Operation(summary = "Registro de propietario")
    @PostMapping("/register/owner")
    public ResponseEntity<String> registerOwner(@RequestBody RegisterRequest registerRequest) {
        Owner owner = new Owner();
        owner.setEmail(registerRequest.getEmail());
        owner.setPassword(registerRequest.getPassword());
        owner.setLastName(registerRequest.getLastName());
        owner.setFirstName(registerRequest.getFirstName());
        owner.setBirthDate(registerRequest.getBirthDate());
        owner.setPhoneNumber(registerRequest.getPhoneNumber());
        ownerRepository.save(owner);
        return new ResponseEntity<>("Propietario registrado correctamente.", HttpStatus.CREATED);
    }


    @Operation(summary = "Registro de arrendatario")
    @PostMapping("/register/tenant")
    public ResponseEntity<String> registerTenant(@RequestBody RegisterRequest registerRequest) {
        Tenant tenant = new Tenant();
        tenant.setEmail(registerRequest.getEmail());
        tenant.setPassword(registerRequest.getPassword());
        tenant.setLastName(registerRequest.getLastName());
        tenant.setFirstName(registerRequest.getFirstName());
        tenant.setBirthDate(registerRequest.getBirthDate());
        tenant.setPhoneNumber(registerRequest.getPhoneNumber());
        tenantRepository.save(tenant);
        return new ResponseEntity<>("Arrendatario registrado correctamente.", HttpStatus.CREATED);
    }

    @Operation(summary = "Logeo de propietario")
    @PostMapping("/login/owner")
    public ResponseEntity<LoginResponse> loginOwner(@RequestBody LoginRequest loginRequest) {
        Owner owner = ownerRepository.findByEmail(loginRequest.getEmail());
        if (owner != null && owner.getPassword().equals(loginRequest.getPassword())) {
            LoginResponse response = new LoginResponse("EXITOSO",owner.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        LoginResponse errorResponse = new LoginResponse( "Fallo de inicio de sesion",null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "Logeo de arrendatario")
    @PostMapping("/login/tenant")
    public ResponseEntity<LoginResponse> loginTenant(@RequestBody LoginRequest loginRequest) {
        Tenant tenant = tenantRepository.findByEmail(loginRequest.getEmail());
        if (tenant != null && tenant.getPassword().equals(loginRequest.getPassword())) {
            // Inicio de sesión exitoso
            LoginResponse response = new LoginResponse("EXITOSO",tenant.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        LoginResponse errorResponse = new LoginResponse( "Fallo de inicio de sesion",null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}