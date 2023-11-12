package com.autoya.autoya_api.autoya.domain.model.controller;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Notification;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Rent;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Requests;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.entities.Vehicule;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.PaymentResponse;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.RentRequest;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.RentStatus;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
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


    @Operation(summary = "Confirmar alquiler por parte del propietario")
    @PutMapping("/confirm/{rentalId}/{tenantId}")
    public ResponseEntity<String> confirmRental(
            @PathVariable Long rentalId,
            @PathVariable Long tenantId) {
        // Implementa la lógica para confirmar una renta por parte del propietario
        Optional<Rent> optionalRental = rentalRepository.findById(rentalId);

        if (optionalRental.isPresent()) {
            Rent rental = optionalRental.get();
            Vehicule vehicle = rental.getVehicle();

            if (vehicle != null && vehicle.getRentStatus() == RentStatus.REQUIRED) {
                // Actualiza el estado del vehículo a "confirmado"
                vehicle.setRentStatus(RentStatus.CONFIRMED);

                // Asigna el tenant al vehículo
                Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
                if (tenant != null) {
                    vehicle.setTenant(tenant);


                    // Actualiza el atributo startDate a la fecha actual
                    rental.setStartDate(new Date());

                    // Suma el atributo amoutthetime a la fecha actual para obtener endDate
                    Long amoutthetime = vehicle.getAmoutthetime();
                    if (amoutthetime != null && amoutthetime > 0) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        calendar.add(Calendar.DAY_OF_YEAR, amoutthetime.intValue());
                        rental.setEndDate(calendar.getTime());
                    }


                    vehiculeRepository.save(vehicle);

                    // Asigna el tenant al contrato de alquiler
                    rental.setTenant(tenant);
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
    }

    @Operation(summary = "Cancelar alquiler por parte del propietario")
    @PutMapping("/cancel/{rentalId}/{tenantId}")
    public ResponseEntity<String> cancelRental(
            @PathVariable Long rentalId,
            @PathVariable Long tenantId) {
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
                    rental.setTenant(tenant);
                    rentalRepository.save(rental);
                    return new ResponseEntity<>("Renta cancelada exitosamente.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Error: Arrendatario no encontrado.", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("Error: El vehículo no está en estado de espera.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Error: Renta no encontrada.", HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Solicitar alquiler por parte del arrendatario")
    @PutMapping("/request")
    public ResponseEntity<String> requestRental(@RequestBody RentRequest rentRequest) {
        // Implementa la lógica para que un arrendatario solicite una renta
        String vehiculeuId = rentRequest.getVehiculeuId();
        Long ownerId = rentRequest.getOwnerId();
        Long tenantId = rentRequest.getTenantId(); // Nuevo atributo

        Optional<Vehicule> optionalVehicle = vehiculeRepository.findById(vehiculeuId);
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId); // Nuevo atributo

        if (optionalVehicle.isPresent() && optionalOwner.isPresent() && optionalTenant.isPresent()) {
            Vehicule vehicle = optionalVehicle.get();
            Owner owner = optionalOwner.get();
            Tenant tenant = optionalTenant.get(); // Nuevo atributo

            if (vehicle.getRentStatus() == RentStatus.WAITING) {
                vehicle.setRentStatus(RentStatus.REQUIRED);
                vehiculeRepository.save(vehicle);

                Rent rental = new Rent();
                rental.setVehicle(vehicle);
                rental.setOwner(owner);
                rental.setTenant(tenant); // Establece al arrendatario

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


    @Operation(summary = "Obtener detalles de alquiler confirmado por owner y vehículo")
    @GetMapping("/payment/{ownerId}/{vehicleId}")
    public ResponseEntity<?> getConfirmedRentalDetails(
            @PathVariable Long ownerId,
            @PathVariable String vehicleId) {
        // Buscar el vehículo con el id proporcionado
        Optional<Vehicule> optionalVehicle = vehiculeRepository.findById(vehicleId);

        if (optionalVehicle.isPresent()) {
            Vehicule vehicle = optionalVehicle.get();
            // Verificar que el rentStatus sea CONFIRMED
            if (vehicle.getRentStatus() == RentStatus.CONFIRMED) {
                // Validar que el vehículo pertenece al owner
                if (vehicle.getOwner().getId().equals(ownerId)) {
                    PaymentResponse paymentResponse = new PaymentResponse();
                    paymentResponse.setPrice(vehicle.getPrice());
                    paymentResponse.setTime(vehicle.getTime());
                    paymentResponse.setAmountthetime(vehicle.getAmoutthetime());
                    paymentResponse.setLocation(vehicle.getLocation());
                    paymentResponse.setImageurl(vehicle.getImageUrl());

                    return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Error: El vehículo no pertenece al owner especificado.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Error: El vehículo no está en estado CONFIRMED.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Error: Vehículo no encontrado.", HttpStatus.NOT_FOUND);
        }
    }

}
