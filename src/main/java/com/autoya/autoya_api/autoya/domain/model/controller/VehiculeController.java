package com.autoya.autoya_api.autoya.domain.model.controller;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Contract;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Notification;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Rent;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Requests;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Vehicule;
import com.autoya.autoya_api.autoya.domain.model.events.requests.ContractRequest;
import com.autoya.autoya_api.autoya.domain.model.events.requests.VehiculeRequest;
import com.autoya.autoya_api.autoya.domain.model.events.requests.VehiculeSearchRequest;
import com.autoya.autoya_api.autoya.domain.model.events.response.RegisterResponse;
import com.autoya.autoya_api.autoya.domain.model.events.response.VehiculeResponse;
import com.autoya.autoya_api.autoya.domain.model.events.response.VehiculeResponseContract;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.*;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehiculeController {
    @Autowired
    private VehiculeRepository vehicleRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private RequestsRepository requestsRepository;
    @Autowired
    private NotificationRepository notificationRepository;


    @Operation(summary = "Registro de vehiculo por el propietario")
    @PostMapping("/owner/register")
    public ResponseEntity<RegisterResponse> registerVehicle(@RequestBody VehiculeRequest request) {
        Vehicule vehicle = new Vehicule();
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setMaxVelocity(request.getMaxVelocity());
        vehicle.setFuelConsumption(request.getFuelConsumption());
        vehicle.setDimensions(request.getDimensions());
        vehicle.setWeight(request.getWeight());
        vehicle.setCarClass(request.getCarClass());
        vehicle.setCarTransmission(request.getCarTransmission());
        vehicle.setLocation(request.getLocation());
        vehicle.setPrice(request.getPrice());
        vehicle.setAmoutthetime(request.getAmoutthetime());
        vehicle.setTime(request.getTime());
        vehicle.setImageUrl(request.getImageUrl());

        Long ownerId = request.getOwnerId();
        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        if (owner == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        vehicle.setOwner(owner);

        Vehicule savedVehicle = vehicleRepository.save(vehicle);

        RegisterResponse response = new RegisterResponse();
        response.setId(savedVehicle.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Devuelve todos los vehiculos")
    @GetMapping("/getAllData")
    public ResponseEntity<List<VehiculeResponse>> getAllVehicles() {


        List<Vehicule> vehicles = vehicleRepository.findAll();
        List<VehiculeResponse> responseList = new ArrayList<>();
        for (Vehicule vehicle : vehicles) {
            if (vehicle.getRentStatus() != RentStatus.CONFIRMED) {
                VehiculeResponse response = new VehiculeResponse();
                response.setId(vehicle.getId());
                response.setBrand(vehicle.getBrand());
                response.setModel(vehicle.getModel());
                response.setMaxVelocity(vehicle.getMaxVelocity());
                response.setFuelConsumption(vehicle.getFuelConsumption());
                response.setDimensions(vehicle.getDimensions());
                response.setWeight(vehicle.getWeight());
                response.setCarClass(vehicle.getCarClass());
                response.setCarTransmission(vehicle.getCarTransmission());
                response.setLocation(vehicle.getLocation());
                response.setPrice(vehicle.getPrice());
                response.setAmoutthetime(vehicle.getAmoutthetime());
                response.setTime(vehicle.getTime());
                response.setOwnerId(vehicle.getOwner().getId());
                response.setOwnername(vehicle.getOwner().getFullName());
                response.setOwnerphone(vehicle.getOwner().getPhoneNumber());
                response.setImageUrl(vehicle.getImageUrl());
                responseList.add(response);
            }
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @Operation(summary = "Buscar vehiculos por parametros")
    @PostMapping("/tenant/search")
    public ResponseEntity<List<VehiculeResponse>> searchVehicles(@RequestBody VehiculeSearchRequest searchRequest) {
        List<Vehicule> foundVehicles = vehicleRepository.findByBrandAndModelAndWeightAndCarClassAndCarTransmissionAndLocationAndPriceAndTimeAndAmoutthetime(
                searchRequest.getBrand(),
                searchRequest.getModel(),
                searchRequest.getWeight(),
                searchRequest.getCarClass(),
                searchRequest.getCarTransmission(),
                searchRequest.getLocation(),
                searchRequest.getPrice(),
                searchRequest.getTime(),
                searchRequest.getAmoutthetime()
        );
        List<VehiculeResponse> responseList = foundVehicles.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    private VehiculeResponse mapToResponse(Vehicule vehicule) {
        VehiculeResponse response = new VehiculeResponse();
        response.setId(vehicule.getId());
        response.setBrand(vehicule.getBrand());
        response.setModel(vehicule.getModel());
        response.setWeight(vehicule.getWeight());
        response.setCarClass(vehicule.getCarClass());
        response.setCarTransmission(vehicule.getCarTransmission());
        response.setLocation(vehicule.getLocation());
        response.setPrice(vehicule.getPrice());
        response.setAmoutthetime(vehicule.getAmoutthetime());
        response.setTime(vehicule.getTime());
        response.setOwnerId(vehicule.getOwner().getId());
        response.setOwnername(vehicule.getOwner().getFullName());
        response.setOwnerphone(vehicule.getOwner().getPhoneNumber());
        response.setImageUrl(vehicule.getImageUrl());

        return response;
    }

    @Operation(summary = "Devuelve todos los vehiculos por el id del owner")
    @GetMapping("/owner/getAll/{ownerId}")
    public ResponseEntity<List<VehiculeResponse>> getVehiclesByOwnerId(@PathVariable Long ownerId) {
        try {
            Owner owner = ownerRepository.findOwnerById(ownerId);

            if (owner == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Vehicule> ownerVehicles = vehicleRepository.findByOwner_Id(ownerId);
            List<VehiculeResponse> responseList = new ArrayList<>();

            for (Vehicule vehicule : ownerVehicles) {
                VehiculeResponse response = mapVehiculeToResponse(vehicule);
                responseList.add(response);
            }
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or handle it according to your needs
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    private VehiculeResponse mapVehiculeToResponse(Vehicule vehicule) {
        VehiculeResponse response = new VehiculeResponse();
        response.setId(vehicule.getId());
        response.setBrand(vehicule.getBrand());
        response.setModel(vehicule.getModel());
        response.setMaxVelocity(vehicule.getMaxVelocity());
        response.setFuelConsumption(vehicule.getFuelConsumption());
        response.setDimensions(vehicule.getDimensions());
        response.setWeight(vehicule.getWeight());
        response.setCarClass(vehicule.getCarClass());
        response.setCarTransmission(vehicule.getCarTransmission());
        response.setImageUrl(vehicule.getImageUrl());
        response.setRentStatus(vehicule.getRentStatus());
        response.setLocation(vehicule.getLocation());
        response.setPrice(vehicule.getPrice());
        response.setTime(vehicule.getTime());
        response.setAmoutthetime(vehicule.getAmoutthetime());
        response.setOwnerId(vehicule.getOwner().getId());
        response.setOwnername(vehicule.getOwner().getFullName());
        response.setOwnerphone(vehicule.getOwner().getPhoneNumber());
        return response;
    }

    @Operation(summary = "Devuelve todos los vehiculos alquilados por el arrendatario")
    @GetMapping("/tenant/rent/{tenantId}")
    public ResponseEntity<List<VehiculeResponse>> getVehiclesByTenantId(@PathVariable Long tenantId) {
        // Obtener todos los alquileres del inquilino por su ID
        List<Rent> tenantRentals = rentalRepository.findByTenant_Id(tenantId);
        List<VehiculeResponse> responseList = new ArrayList<>();

        for (Rent rent : tenantRentals) {
            Vehicule vehicle = rent.getVehicle();
            if (vehicle.getRentStatus() == RentStatus.REQUIRED ||vehicle.getRentStatus() == RentStatus.CONFIRMED ) {
                VehiculeResponse vehiculeResponse = mapToResponses(vehicle);
                responseList.add(vehiculeResponse);
            }
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    private VehiculeResponse mapToResponses(Vehicule vehicle) {
        VehiculeResponse response = new VehiculeResponse();
        response.setId(vehicle.getId());
        response.setBrand(vehicle.getBrand());
        response.setModel(vehicle.getModel());
        response.setMaxVelocity(vehicle.getMaxVelocity());
        response.setFuelConsumption(vehicle.getFuelConsumption());
        response.setDimensions(vehicle.getDimensions());
        response.setWeight(vehicle.getWeight());
        response.setCarClass(vehicle.getCarClass());
        response.setCarTransmission(vehicle.getCarTransmission());
        response.setImageUrl(vehicle.getImageUrl());
        response.setRentStatus(vehicle.getRentStatus());
        response.setLocation(vehicle.getLocation());
        response.setPrice(vehicle.getPrice());
        response.setTime(vehicle.getTime());
        response.setAmoutthetime(vehicle.getAmoutthetime());
        response.setOwnerId(vehicle.getOwner().getId());
        response.setOwnername(vehicle.getOwner().getFullName());
        response.setOwnerphone(vehicle.getOwner().getPhoneNumber());
        return response;
    }



    @Operation(summary = "Crea contrato de vehiculo de owner por id de owner y de vehiculo")
    @PostMapping("/register/owner/create-contract")
    public ResponseEntity<String> createContract(@RequestBody ContractRequest contractRequest) {
        String vehiculeuId = contractRequest.getVehicleId();
        Long ownerId = contractRequest.getOwnerId();

        Optional<Vehicule> optionalVehicule = vehicleRepository.findById(vehiculeuId);
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);

        if (optionalVehicule.isPresent() && optionalOwner.isPresent()) {
            Vehicule vehicule = optionalVehicule.get();
            Owner owner = optionalOwner.get();
            Contract contract = new Contract();
            contract.setVehicle(vehicule);
            contract.setOwner(owner);
            contract.setPdf(contractRequest.getPdf());
            contractRepository.save(contract);

            return new ResponseEntity<>("Contrato creado exitosamente.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error: Vehículo u propietario no encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Devolver datos de vehiculo y owner para pagina de contrato")
    @GetMapping("/owner/{vehicleId}/{ownerId}")
    public ResponseEntity<VehiculeResponseContract> getVehiculeAndOwnerDetails(
            @PathVariable String vehicleId,
            @PathVariable Long ownerId) {
        Vehicule vehicule = vehicleRepository.findById(vehicleId).orElse(null);

        if (vehicule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!vehicule.getOwner().getId().equals(ownerId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        VehiculeResponseContract response = new VehiculeResponseContract();
        response.setId(vehicule.getId());
        response.setBrand(vehicule.getBrand());
        response.setModel(vehicule.getModel());
        response.setImageurl(vehicule.getImageUrl());

        Owner owner = vehicule.getOwner();
        response.setOwnerId(owner.getId());
        response.setOwnername(owner.getFullName());
        response.setOwnerphone(owner.getPhoneNumber());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{ownerId}/{vehiculeId}")
    public ResponseEntity<String> deleteVehicule(@PathVariable Long ownerId, @PathVariable String vehiculeId) {
        Vehicule vehicule = vehicleRepository.findById(vehiculeId).orElse(null);

        if (vehicule == null) {
            return new ResponseEntity<>("Error: Vehículo no encontrado.", HttpStatus.NOT_FOUND);
        }
        List<Notification> notifications = notificationRepository.findByVehicleId(vehiculeId);
        List<Requests> requests = requestsRepository.findByVehicleId(vehiculeId);
        List<Rent> rents=rentalRepository.findByVehicleId(vehiculeId);
        List<Contract> contracts=contractRepository.findByVehicleId(vehiculeId);
        if(!rents.isEmpty()){
            rentalRepository.deleteAll(rents);
        }
        if (!notifications.isEmpty()) {
            notificationRepository.deleteAll(notifications);
        }
        if (!requests.isEmpty()) {
            requestsRepository.deleteAll(requests);
        }
        if (!contracts.isEmpty()){
            contractRepository.deleteAll(contracts);
        }

        vehicleRepository.deleteById(vehiculeId);

        return new ResponseEntity<>("Vehículo eliminado exitosamente.", HttpStatus.OK);
    }

}