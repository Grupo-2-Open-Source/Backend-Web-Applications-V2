package com.autoya.autoya_api.autoya.domain.model.controller;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Notification;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Rent;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Requests;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.entities.Vehicule;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.*;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/api/v1/rentals")
public class RentController {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private RequestsRepository requestsRepository;

    @Operation(summary = "Obtener lista de rentas por ID de vehículo y propietario")
    @GetMapping("/getAllRentOwner/{ownerId}/{vehicleId}")
    public ResponseEntity<List<RentOwnerResponse>> getRentRequestsByOwnerAndVehicle(
            @PathVariable Long ownerId,
            @PathVariable String vehicleId) {
        Optional<Vehicule> optionalVehicle = vehiculeRepository.findById(vehicleId);

        if (optionalVehicle.isPresent()) {
            Vehicule vehicle = optionalVehicle.get();
            // Verificar que el vehículo pertenezca al propietario especificado
            if (vehicle.getOwner().getId().equals(ownerId)) {
                List<Rent> rentRequests = rentalRepository.findByVehicleId(vehicleId);

                List<RentOwnerResponse> responseList = rentRequests.stream()
                        .map(rent -> new RentOwnerResponse(
                                rent.getId(),
                                rent.getTenant().getId(),
                                vehicle.getImageUrl(),
                                vehicle.getBrand(),
                                vehicle.getModel(),
                                vehicle.getRentStatus()
                        ))
                        .collect(Collectors.toList());

                return new ResponseEntity<>(responseList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Confirmar alquiler por parte del propietario")
    @PutMapping("/confirm/{rentalId}/{tenantId}")
    @Transactional
    public ResponseEntity<String> confirmRental(
            @PathVariable Long rentalId,
            @PathVariable Long tenantId) {
        try {
            Optional<Rent> optionalRental = rentalRepository.findById(rentalId);
            if (optionalRental.isPresent()) {
                Rent rental = optionalRental.get();
                Vehicule vehicle = rental.getVehicle();
                if (vehicle != null && vehicle.getRentStatus() == RentStatus.REQUIRED) {
                    vehicle.setRentStatus(RentStatus.CONFIRMED);
                    Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
                    if (tenant != null) {
                        vehicle.setTenant(tenant);
                        vehiculeRepository.save(vehicle);
                        rentalRepository.save(rental);
                        return new ResponseEntity<>("Renta confirmada exitosamente.", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("Error: Arrendatario no encontrado.", HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>("Error: El vehículo no está en estado de espera.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Error: Renta no encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al confirmar la renta", e);
            return new ResponseEntity<>("Error al confirmar la renta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Cancelar alquiler por parte del propietario")
    @PutMapping("/cancel/{rentalId}/{tenantId}")
    @Transactional
    public ResponseEntity<String> cancelRental(
            @PathVariable Long rentalId,
            @PathVariable Long tenantId) {
        try {
            Optional<Rent> optionalRental = rentalRepository.findById(rentalId);
            if (optionalRental.isPresent()) {
                Rent rental = optionalRental.get();
                Vehicule vehicle = rental.getVehicle();
                if (vehicle != null && vehicle.getRentStatus() == RentStatus.REQUIRED) {
                    vehicle.setRentStatus(RentStatus.CANCELLED);
                    Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
                    if (tenant != null) {
                        vehicle.setTenant(tenant);
                        vehiculeRepository.save(vehicle);
                        rentalRepository.save(rental);
                        return new ResponseEntity<>("Renta cancelada exitosamente.", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("Error: Arrendatario no encontrado.", HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>("Error: El vehículo no está solicitado.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Error: Renta no encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al confirmar la renta", e);
            return new ResponseEntity<>("Error al confirmar la renta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Solicitar alquiler por parte del arrendatario")
    @PutMapping("/request")
    public ResponseEntity<String> requestRental(@RequestBody RentRequest rentRequest) {
        String vehiculeuId = rentRequest.getVehiculeId();
        Long ownerId = rentRequest.getOwnerId();
        Long tenantId = rentRequest.getTenantId();

        Optional<Vehicule> optionalVehicle = vehiculeRepository.findById(vehiculeuId);
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);

        if (optionalVehicle.isPresent() && optionalOwner.isPresent() && optionalTenant.isPresent()) {
            Vehicule vehicle = optionalVehicle.get();
            Owner owner = optionalOwner.get();
            Tenant tenant = optionalTenant.get();

            if (vehicle.getRentStatus() == RentStatus.WAITING) {
                vehicle.setRentStatus(RentStatus.REQUIRED);
                vehiculeRepository.save(vehicle);

                Rent rental = new Rent();
                rental.setVehicle(vehicle);
                rental.setOwner(owner);
                rental.setTenant(tenant);

                rentalRepository.save(rental);


                Notification notification = new Notification();
                notification.setOwner(owner);
                notification.setTenant(tenant);
                notification.setVehicle(vehicle);
                notificationRepository.save(notification);


                Requests requests=new Requests();
                requests.setOwner(owner);
                requests.setTenant(tenant);
                requests.setVehicle(vehicle);
                requestsRepository.save(requests);


                return new ResponseEntity<>("Solicitud de renta enviada exitosamente.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error: El vehículo no está disponible para alquiler.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Error: Vehículo, propietario o arrendatario no encontrado.", HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Obtener detalles para pago de alquiler confirmado por owner y vehículo")
    @GetMapping("/payment/{ownerId}/{vehicleId}")
    public ResponseEntity<PaymentResponse> getConfirmedRentalDetails(
            @PathVariable Long ownerId,
            @PathVariable String vehicleId) {
        Optional<Vehicule> optionalVehicle = vehiculeRepository.findById(vehicleId);

        if (optionalVehicle.isPresent()) {
            Vehicule vehicle = optionalVehicle.get();
            if (vehicle.getRentStatus() == RentStatus.CONFIRMED) {
                if (vehicle.getOwner().getId().equals(ownerId)) {
                    PaymentResponse paymentResponse = new PaymentResponse();
                    paymentResponse.setPrice(vehicle.getPrice());
                    paymentResponse.setTime(vehicle.getTime());
                    paymentResponse.setAmountthetime(vehicle.getAmoutthetime());
                    paymentResponse.setLocation(vehicle.getLocation());
                    paymentResponse.setImageurl(vehicle.getImageUrl());

                    return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
