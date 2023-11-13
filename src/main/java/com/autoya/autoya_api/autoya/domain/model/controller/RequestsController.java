package com.autoya.autoya_api.autoya.domain.model.controller;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Requests;
import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Vehicule;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.RequestsResponse;
import com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories.RequestsRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestsController {
    @Autowired
    private RequestsRepository requestsRepository;


    @Operation(summary = "Devuelve una lista de owner a los cuales se les ha enviado la solicitud de alquiler")
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<RequestsResponse>> getRequestsByTenantId(@PathVariable Long tenantId) {
        List<Requests> requests = requestsRepository.findBytenant_Id(tenantId);
        List<RequestsResponse> responseList = new ArrayList<>();

        for (Requests requests1: requests) {
            Owner owner= requests1.getOwner();
            Vehicule vehicle = requests1.getVehicle();

            RequestsResponse response = new RequestsResponse();
            response.setOwnerName(owner.getFullName());
            response.setVehicleBrand(vehicle.getBrand());
            response.setVehicleModel(vehicle.getModel());
            response.setRentstatus(vehicle.getRentStatus().toString());
            response.setImageurl(vehicle.getImageUrl());

            responseList.add(response);
        }

        return new ResponseEntity<>(responseList,HttpStatus.OK);

    }
}
