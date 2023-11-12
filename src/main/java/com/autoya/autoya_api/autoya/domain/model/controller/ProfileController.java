package com.autoya.autoya_api.autoya.domain.model.controller;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Images;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.ProfileRequest;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.ProfileResponse;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.ImageRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.OwnerRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.TenantRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Operation(summary = "Obtiene el propietario por id")
    @GetMapping("/owner/{id}")
    public ResponseEntity<ProfileResponse> getOwnerById(@PathVariable Long id) {
        Owner owner = ownerRepository.findById(id).orElse(null);
        if (owner != null) {
            ProfileResponse response = new ProfileResponse();
            response.setId(owner.getId());
            response.setEmail(owner.getEmail());
            response.setPassword(owner.getPassword());
            response.setLastName(owner.getLastName());
            response.setFirstName(owner.getFirstName());
            response.setBirthDate(owner.getBirthDate());
            response.setPhoneNumber(owner.getPhoneNumber());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Obtiene el arrendatario por id")
    @GetMapping("/tenant/{id}")
    public ResponseEntity<ProfileResponse> getTenantById(@PathVariable Long id) {
        Tenant tenant = tenantRepository.findById(id).orElse(null);
        if (tenant != null) {
            ProfileResponse response = new ProfileResponse();
            response.setId(tenant.getId());
            response.setEmail(tenant.getEmail());
            response.setPassword(tenant.getPassword());
            response.setLastName(tenant.getLastName());
            response.setFirstName(tenant.getFirstName());
            response.setBirthDate(tenant.getBirthDate());
            response.setPhoneNumber(tenant.getPhoneNumber());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Obtiene todos los propietarios")
    @GetMapping("/owners")
    public ResponseEntity<List<ProfileResponse>> getAllOwners() {
        List<Owner> owners = ownerRepository.findAll();
        List<ProfileResponse> responseList = new ArrayList<>();

        for (Owner owner : owners) {
            ProfileResponse response = new ProfileResponse();
            response.setId(owner.getId());
            response.setEmail(owner.getEmail());
            response.setPassword(owner.getPassword());
            response.setLastName(owner.getLastName());
            response.setFirstName(owner.getFirstName());
            response.setBirthDate(owner.getBirthDate());
            response.setPhoneNumber(owner.getPhoneNumber());
            responseList.add(response);
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    @Operation(summary = "Obtiene todos los arrendatarios")
    @GetMapping("/tenants")
    public ResponseEntity<List<ProfileResponse>> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        List<ProfileResponse> responseList = new ArrayList<>();

        for (Tenant tenant : tenants) {
            ProfileResponse response = new ProfileResponse();
            response.setId(tenant.getId());
            response.setEmail(tenant.getEmail());
            response.setPassword(tenant.getPassword());
            response.setLastName(tenant.getLastName());
            response.setFirstName(tenant.getFirstName());
            response.setBirthDate(tenant.getBirthDate());
            response.setPhoneNumber(tenant.getPhoneNumber());
            responseList.add(response);
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }


    //cagar imagen
    @Operation(summary = "Registra imagen de perfil de propietario")
    @PostMapping("/owner/images")
    public ResponseEntity<String> uploadImageByURL(@RequestParam("imageUrl") String imageUrl,
            @RequestParam("ownerId") Long ownerId
    )
    {
        // Validaciones y procesamiento de la URL de la imagen
        if (imageUrl == null || imageUrl.isEmpty()) {
            return new ResponseEntity<>("Error: La URL de la imagen está vacía.", HttpStatus.BAD_REQUEST);
        }

        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        if (owner == null) {
            return new ResponseEntity<>("Error: Propietario no encontrado.", HttpStatus.NOT_FOUND);
        }

        // Guarda la URL de la imagen en tu base de datos.
        Images image = new Images();
        image.setOwner(owner);
        image.setImageUrl(imageUrl);

        // Guarda la entidad en la base de datos (debes tener un repositorio para Images).
        imageRepository.save(image);

        return new ResponseEntity<>("Imagen registrada correctamente.", HttpStatus.CREATED);
    }
    @Operation(summary = "Registra imagen de perfil de arrendatario")
    @PostMapping("/tenant/images")
    public ResponseEntity<String> uploadImageByURLs(
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("tenantId") Long tenantId
    ) {
        // Validaciones y procesamiento de la URL de la imagen
        if (imageUrl == null || imageUrl.isEmpty()) {
            return new ResponseEntity<>("Error: La URL de la imagen está vacía.", HttpStatus.BAD_REQUEST);
        }

        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        if (tenant == null) {
            return new ResponseEntity<>("Error: Arrendatario no encontrado.", HttpStatus.NOT_FOUND);
        }

        // Guarda la URL de la imagen en tu base de datos.
        Images image = new Images();
        image.setTenant(tenant);
        image.setImageUrl(imageUrl);

        // Guarda la entidad en la base de datos (debes tener un repositorio para Images).
        imageRepository.save(image);

        return new ResponseEntity<>("Imagen registrada correctamente.", HttpStatus.CREATED);
    }


    @Operation(summary = "Devuelve imagen de perfil de propietario por id")
    @GetMapping("/{id}/image-url")
    public ResponseEntity<String> getImageByOnwerId(@PathVariable Long id) {
        Images image = imageRepository.findByOwner_Id(id);
        if (image != null) {
            return new ResponseEntity<>(image.getImageUrl(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Imagen no encontrada para el ID proporcionado.", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Devuelve imagen de perfil de arrendatario por id")
    @GetMapping("/images/tenant/{tenantId}")
    public ResponseEntity<Images> getImageByTenantId(@PathVariable Long tenantId) {
        Images image = imageRepository.findByTenant_Id(tenantId);

        if (image != null) {
            return new ResponseEntity<>(image, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Actualiza imagen de perfil de propietario por id")
    @PutMapping("/owner/{id}/image-url")
    public ResponseEntity<String> updateOwnerImageUrl(@PathVariable Long id, @RequestParam("imageUrl") String imageUrl) {
        Owner owner = ownerRepository.findById(id).orElse(null);

        if (owner != null) {
            Images images = imageRepository.findByOwner_Id(id);

            if (images != null) {
                images.setImageUrl(imageUrl); // Actualiza la propiedad imageUrl en la entidad Images
                imageRepository.save(images);

                return new ResponseEntity<>("Imagen del Owner actualizada correctamente.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Imagen del Owner no encontrada.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Owner no encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualiza imagen de perfil de arrendatario por id")
    @PutMapping("/tenant/{id}/image-url")
    public ResponseEntity<String> updateTenantImageUrl(@PathVariable Long id, @RequestParam("imageUrl") String imageUrl) {
        Tenant tenant = tenantRepository.findById(id).orElse(null);

        if (tenant != null) {
            Images images = imageRepository.findByTenant_Id(id);

            if (images != null) {
                images.setImageUrl(imageUrl); // Actualiza la propiedad imageUrl en la entidad Images
                imageRepository.save(images);

                return new ResponseEntity<>("Imagen del Tenant actualizada correctamente.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Imagen del Tenant no encontrada.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Tenant no encontrado.", HttpStatus.NOT_FOUND);
        }
    }


    //actualizar datos del profile
    @Operation(summary = "Actualiza los datos de propietario por id")
    @PutMapping("/owner/{id}/profile")
    public ResponseEntity<String> updateOwnerProfile(
            @PathVariable Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String phoneNumber
    ) {
        Owner owner = ownerRepository.findById(id).orElse(null);
        if (owner != null) {
            if (email != null) {owner.setEmail(email);}
            if (lastName != null) {owner.setLastName(lastName);}
            if (firstName != null) {owner.setFirstName(firstName);}
            if (phoneNumber != null) {owner.setPhoneNumber(phoneNumber);}
            ownerRepository.save(owner);
            return new ResponseEntity<>("Perfil del Owner actualizado correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Owner no encontrado.", HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Actualiza los datos de arrendatario por id")
    @PutMapping("/tenant/{id}/profile")
    public ResponseEntity<String> updateTenantProfile(
            @PathVariable Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String phoneNumber
    ) {
        Tenant tenant = tenantRepository.findById(id).orElse(null);
        if (tenant != null) {
            if (email != null) {tenant.setEmail(email);}
            if (lastName != null) {tenant.setLastName(lastName);}
            if (firstName != null) {tenant.setFirstName(firstName);}
            if (phoneNumber != null) {tenant.setPhoneNumber(phoneNumber);}
            tenantRepository.save(tenant);
            return new ResponseEntity<>("Perfil del Tenant actualizado correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tenant no encontrado.", HttpStatus.NOT_FOUND);
        }
    }

}