package com.autoya.autoya_api.autoya.domain.model.controller;

import com.autoya.autoya_api.autoya.domain.model.aggregate.CriminalRecords;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Images;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.events.requests.CriminalRecordsRequests;
import com.autoya.autoya_api.autoya.domain.model.events.requests.ImageRequestOwner;
import com.autoya.autoya_api.autoya.domain.model.events.requests.ImageRequestTenant;
import com.autoya.autoya_api.autoya.domain.model.events.requests.UpdateProfileRequest;
import com.autoya.autoya_api.autoya.domain.model.events.response.CriminalRecordsResponse;
import com.autoya.autoya_api.autoya.domain.model.events.response.ImageResponse;
import com.autoya.autoya_api.autoya.domain.model.events.response.ProfileResponse;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.CriminalRequestsRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.ImageRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.OwnerRepository;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.TenantRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private CriminalRequestsRepository criminalRequestsRepository;

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


    @Operation(summary = "Registra imagen de perfil de propietario")
    @PostMapping("/owner/image")
    public ResponseEntity<String> uploadImage(@RequestBody ImageRequestOwner imageRequestOwner) {
        String imageUrl = imageRequestOwner.getImageUrl();
        Long ownerId = imageRequestOwner.getOwnerId();
        if (imageUrl == null || imageUrl.isEmpty()) {
            return new ResponseEntity<>("Error: La URL de la imagen está vacía.", HttpStatus.BAD_REQUEST);
        }
        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        if (owner == null) {
            return new ResponseEntity<>("Error: Propietario no encontrado.", HttpStatus.NOT_FOUND);
        }
        Images image = new Images();
        image.setOwner(owner);
        image.setImageUrl(imageUrl);
        imageRepository.save(image);
        return new ResponseEntity<>("Imagen registrada correctamente.", HttpStatus.CREATED);
    }
    @Operation(summary = "Registra imagen de perfil de arrendatario")
    @PostMapping("/tenant/image")
    public ResponseEntity<String> uploadImageByURLs(
            @RequestBody ImageRequestTenant imageRequestTenant
    ) {
        String imageUrl= imageRequestTenant.getImageUrl();
        Long tenantId= imageRequestTenant.getTenantId();
        if (imageUrl == null || imageUrl.isEmpty()) {
            return new ResponseEntity<>("Error: La URL de la imagen está vacía.", HttpStatus.BAD_REQUEST);
        }
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        if (tenant == null) {
            return new ResponseEntity<>("Error: Arrendatario no encontrado.", HttpStatus.NOT_FOUND);
        }
        Images image = new Images();
        image.setTenant(tenant);
        image.setImageUrl(imageUrl);
        imageRepository.save(image);
        return new ResponseEntity<>("Imagen registrada correctamente.", HttpStatus.CREATED);
    }

    @Operation(summary = "Devuelve imagen de perfil de propietario por id")
    @GetMapping("/owner/image-url/get/{id}")
    public ResponseEntity<ImageResponse> getImageByOwnerId(@PathVariable Long id) {
        Images image = imageRepository.findByOwner_Id(id);
        if (image != null) {
            ImageResponse response = new ImageResponse();
            response.setImageUrl(image.getImageUrl());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Devuelve imagen de perfil de arrendatario por id")
    @GetMapping("/tenant/image-url/get/{id}")
    public ResponseEntity<ImageResponse> getImageByTenantId(@PathVariable Long id) {
        Images image = imageRepository.findByTenant_Id(id);
        if (image != null) {
            ImageResponse response=new ImageResponse();
            response.setImageUrl(image.getImageUrl());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "Actualiza la imagen de perfil de owner")
    @PutMapping("/owner/image-url/put")
    public ResponseEntity<String> updateOwnerImageUrl(@RequestBody ImageRequestOwner imageRequestOwner) {
        String imageUrl = imageRequestOwner.getImageUrl();
        if (imageUrl == null || imageUrl.isEmpty()) {
            return new ResponseEntity<>("Error: La URL de la imagen está vacía.", HttpStatus.BAD_REQUEST);
        }
        Long ownerId = imageRequestOwner.getOwnerId();
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner != null) {
            Images images = owner.getImages();
            if (images != null) {
                images.setImageUrl(imageUrl);
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
    @PutMapping("/tenant/image-url/put")
    public ResponseEntity<String> updateTenantImageUrl(@RequestBody ImageRequestTenant imageRequestTenant) {
        String imageUrl=imageRequestTenant.getImageUrl();
        if (imageUrl==null|| imageUrl.isEmpty()){
            return new ResponseEntity<>("Error: La URL de la imagen está vacía.", HttpStatus.BAD_REQUEST);
        }
        Long tenantId=imageRequestTenant.getTenantId();
        Tenant tenant = tenantRepository.findTenantById(tenantId);
        if (tenant != null) {
            Images images = tenant.getImages();
            if (images != null) {
                images.setImageUrl(imageUrl);
                imageRepository.save(images);
                return new ResponseEntity<>("Imagen del Tenant actualizada correctamente.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Imagen del Tenant no encontrada.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Tenant no encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualiza los datos de propietario por id")
    @PutMapping("/owner/update/data/profile")
    public ResponseEntity<String> updateOwnerProfile(@RequestBody UpdateProfileRequest request) {
        Long id = request.getId();
        Owner owner = ownerRepository.findById(id).orElse(null);
        if (owner != null) {
            if (request.getEmail() != null) {owner.setEmail(request.getEmail());}
            if (request.getLastName() != null) {owner.setLastName(request.getLastName());}
            if (request.getFirstName() != null) {owner.setFirstName(request.getFirstName());}
            if (request.getPhoneNumber() != null) {owner.setPhoneNumber(request.getPhoneNumber());}
            ownerRepository.save(owner);
            return new ResponseEntity<>("Perfil del Owner actualizado correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Owner no encontrado.", HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Actualiza los datos de arrendatario por id")
    @PutMapping("/tenant/update/data/profile")
    public ResponseEntity<String> updateTenantProfile(@RequestBody UpdateProfileRequest request) {
        Long id=request.getId();
        Tenant tenant = tenantRepository.findById(id).orElse(null);
        if (tenant != null) {
            if (request.getEmail() != null) {tenant.setEmail(request.getEmail());}
            if (request.getLastName() != null) {tenant.setLastName(request.getLastName());}
            if (request.getFirstName()!= null) {tenant.setFirstName(request.getFirstName());}
            if (request.getPhoneNumber() != null) {tenant.setPhoneNumber(request.getPhoneNumber());}
            tenantRepository.save(tenant);
            return new ResponseEntity<>("Perfil del Tenant actualizado correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tenant no encontrado.", HttpStatus.NOT_FOUND);
        }
    }




    @Operation(summary = "Sube documentos de Antecedentes Penales por id de arrendatario")
    @PostMapping("/tenant/documents/criminal-records")
    public ResponseEntity<String> uploadDoc(@RequestBody CriminalRecordsRequests criminalRecordsRequests) {
        String pdfUrl = criminalRecordsRequests.getPdfUrl();
        Long tenantId = criminalRecordsRequests.getTenantId();
        if (pdfUrl == null || pdfUrl.isEmpty()) {
            return new ResponseEntity<>("Error: La URL del documento está vacía.", HttpStatus.BAD_REQUEST);
        }
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        if (tenant == null) {
            return new ResponseEntity<>("Error: Arrendatario no encontrado.", HttpStatus.NOT_FOUND);
        }
        CriminalRecords criminalRecords = new CriminalRecords();
        criminalRecords.setTenant(tenant);
        criminalRecords.setPdf(pdfUrl);
        criminalRequestsRepository.save(criminalRecords);
        return new ResponseEntity<>("Documento registrada correctamente.", HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene el documento PDF de Antecedentes Penales por id de arrendatario")
    @GetMapping("/owner/documents/criminal-records-of-tenant/{id}")
    public ResponseEntity<CriminalRecordsResponse> getPdfByTenantId(@PathVariable Long id) {
        CriminalRecords criminalRecords = criminalRequestsRepository.findByTenant_Id(id);
        if (criminalRecords != null) {
            CriminalRecordsResponse response = new CriminalRecordsResponse();
            response.setPdf(criminalRecords.getPdf());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}