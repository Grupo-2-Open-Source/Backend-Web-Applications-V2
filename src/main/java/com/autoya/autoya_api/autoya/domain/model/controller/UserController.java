package com.autoya.autoya_api.autoya.domain.model.controller;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.events.requests.LoginRequest;
import com.autoya.autoya_api.autoya.domain.model.events.requests.RegisterRequest;
import com.autoya.autoya_api.autoya.domain.model.events.response.LoginResponse;
import com.autoya.autoya_api.autoya.domain.model.events.response.RegisterResponseOwner;
import com.autoya.autoya_api.autoya.domain.model.events.response.RegisterResponseTenant;
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

/**
 * Controller class for handling user-related operations, such as registration and login.
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private TenantRepository tenantRepository;

    /**
     * POST /api/v1/user/register/owner
     * <p>Endpoint that created a owner</p>
     * @param registerRequest  the resource with  the information to create the owner
     * @return the created owner
     */
    @Operation(summary = "Registro de propietario")
    @PostMapping("/register/owner")
    public ResponseEntity<RegisterResponseOwner> registerOwner(@RequestBody RegisterRequest registerRequest) {
        Owner owner = new Owner();
        owner.setEmail(registerRequest.getEmail());
        owner.setPassword(registerRequest.getPassword());
        owner.setLastName(registerRequest.getLastName());
        owner.setFirstName(registerRequest.getFirstName());
        owner.setBirthDate(registerRequest.getBirthDate());
        owner.setPhoneNumber(registerRequest.getPhoneNumber());
        ownerRepository.save(owner);

        // Crear una instancia de RegisterResponseOwner con el ID del propietario recién registrado
        RegisterResponseOwner response = new RegisterResponseOwner(owner.getId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * POST /api/v1/user/register/tenant
     * <p>Endpoint that created a tenant</p>
     * @param registerRequest  the resource with  the information to create the tenant
     * @return the created tenant
     */
    @Operation(summary = "Registro de arrendatario")
    @PostMapping("/register/tenant")
    public ResponseEntity<RegisterResponseTenant> registerTenant(@RequestBody RegisterRequest registerRequest) {
        Tenant tenant = new Tenant();
        tenant.setEmail(registerRequest.getEmail());
        tenant.setPassword(registerRequest.getPassword());
        tenant.setLastName(registerRequest.getLastName());
        tenant.setFirstName(registerRequest.getFirstName());
        tenant.setBirthDate(registerRequest.getBirthDate());
        tenant.setPhoneNumber(registerRequest.getPhoneNumber());
        tenantRepository.save(tenant);
        RegisterResponseTenant response =new RegisterResponseTenant(tenant.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    /**
     * POST /api/v1/user/register/owner
     * <p>Endpoint that login</p>
     * @param loginRequest the resource with  the information to login
     * @return login
     */
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
    /**
     * POST /api/v1/user/register/tenant
     * <p>Endpoint that login</p>
     * @param loginRequest the resource with  the information to login
     * @return login
     */
    @Operation(summary = "Logeo de arrendatario")
    @PostMapping("/login/tenant")
    public ResponseEntity<LoginResponse> loginTenant(@RequestBody LoginRequest loginRequest) {
        Tenant tenant = tenantRepository.findByEmail(loginRequest.getEmail());
        if (tenant != null && tenant.getPassword().equals(loginRequest.getPassword())) {
            LoginResponse response = new LoginResponse("EXITOSO",tenant.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        LoginResponse errorResponse = new LoginResponse( "Fallo de inicio de sesion",null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}