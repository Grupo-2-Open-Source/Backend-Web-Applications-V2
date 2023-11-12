package com.autoya.autoya_api.autoya.domain.model.controller;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Contract;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Notification;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Rent;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Requests;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Vehicule;
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
    public ResponseEntity<String> registerVehicle(@RequestBody VehiculeRequest request) {
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
            return new ResponseEntity<>("Propietario no encontrado.", HttpStatus.NOT_FOUND);
        }
        vehicle.setOwner(owner);
        vehicleRepository.save(vehicle);
        return new ResponseEntity<>("Vehículo registrado correctamente.", HttpStatus.CREATED);
    }

    @Operation(summary = "Devuelve todos los vehiculos")
    @GetMapping("/vehicules/all")
    public ResponseEntity<List<VehiculeResponse>> getAllVehicles() {


        List<Vehicule> vehicles = vehicleRepository.findAll();
        List<VehiculeResponse> responseList = new ArrayList<>();
        for (Vehicule vehicle : vehicles) {
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

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @Operation(summary = "Buscar vehiculos por parametros")
    @PostMapping("/owner/search")
    public ResponseEntity<List<VehiculeResponse>> searchVehicles(@RequestBody VehiculeSearchRequest searchRequest) {
        List<Vehicule> foundVehicles = vehicleRepository.findByBrandAndModelAndMaxVelocityAndFuelConsumptionAndDimensionsAndWeightAndCarClassAndCarTransmissionAndLocationAndPriceAndRentTime(
                searchRequest.getBrand(),
                searchRequest.getModel(),
                searchRequest.getMaxVelocity(),
                searchRequest.getFuelConsumption(),
                searchRequest.getDimensions(),
                searchRequest.getWeight(),
                searchRequest.getCarClass(),
                searchRequest.getCarTransmission(),
                searchRequest.getLocation(),
                searchRequest.getPrice(),
                searchRequest.getRentTime()
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
        response.setMaxVelocity(vehicule.getMaxVelocity());
        response.setFuelConsumption(vehicule.getFuelConsumption());
        response.setDimensions(vehicule.getDimensions());
        response.setWeight(vehicule.getWeight());
        response.setCarClass(vehicule.getCarClass());
        response.setCarTransmission(vehicule.getCarTransmission());
        response.setLocation(vehicule.getLocation());
        response.setPrice(vehicule.getPrice());
        response.setAmoutthetime(vehicule.getAmoutthetime());
        response.setTime(vehicule.getTime());
        response.setOwnerId(vehicule.getOwner().getId());

        return response;
    }

    @Operation(summary = "Devuelve todos los vehiculos por el id del owner")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<VehiculeResponse>> getVehiclesByOwnerId(@PathVariable Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        if (owner == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Vehicule> ownerVehicles = vehicleRepository.findByOwner_Id(ownerId);
        List<VehiculeResponse> responseList = ownerVehicles.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }


    @Operation(summary = "Devuelve todos los vehiculos alquilados por el arrendatario")
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<VehiculeResponse>> getVehiclesByTenantId(@PathVariable Long tenantId) {
        // Obtener todos los alquileres del inquilino por su ID
        List<Rent> tenantRentals = rentalRepository.findByTenant_Id(tenantId);
        List<Vehicule> tenantVehicles = tenantRentals.stream()
                .map(Rent::getVehicle)
                .collect(Collectors.toList());
        tenantVehicles = tenantVehicles.stream()
                .filter(vehicle -> vehicle.getRentStatus() == RentStatus.CONFIRMED)
                .collect(Collectors.toList());
        List<VehiculeResponse> responseList = tenantVehicles.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseList, HttpStatus.OK);
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
        // Buscar el vehículo por su ID
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
        response.setOwnername(owner.getFullName()); // Asumiendo un método para obtener el nombre del propietario
        response.setOwnerphone(owner.getPhoneNumber()); // Asumiendo un método para obtener el teléfono del propietario

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{ownerId}/{vehiculeId}")
    public ResponseEntity<String> deleteVehicule(@PathVariable Long ownerId, @PathVariable String vehiculeId) {
        // Busca el vehículo que se va a eliminar
        Vehicule vehicule = vehicleRepository.findById(vehiculeId).orElse(null);

        if (vehicule == null) {
            return new ResponseEntity<>("Error: Vehículo no encontrado.", HttpStatus.NOT_FOUND);
        }

        List<Notification> notifications = notificationRepository.findByVehicleId(vehiculeId);

        // Verificar si existen solicitudes relacionadas con el vehículo
        List<Requests> requests = requestsRepository.findByVehicleId(vehiculeId);
        List<Rent> rents=rentalRepository.findByVehicleId(vehiculeId);

        // Si hay notificaciones o solicitudes relacionadas con el vehículo, eliminarlas primero

        if(!rents.isEmpty()){
            rentalRepository.deleteAll(rents);
        }
        if (!notifications.isEmpty()) {
            // Eliminar notificaciones
            notificationRepository.deleteAll(notifications);
        }

        if (!requests.isEmpty()) {
            // Eliminar solicitudes
            requestsRepository.deleteAll(requests);
        }

        // Luego, eliminar el vehículo
        vehicleRepository.deleteById(vehiculeId);

        return new ResponseEntity<>("Vehículo eliminado exitosamente.", HttpStatus.OK);
    }

}