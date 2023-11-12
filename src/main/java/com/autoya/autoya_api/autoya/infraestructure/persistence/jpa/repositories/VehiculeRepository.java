package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.entities.Vehicule;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.CarClass;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.CarTransmision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, String> {
    List<Vehicule> findByBrandAndModelAndMaxVelocityAndFuelConsumptionAndDimensionsAndWeightAndCarClassAndCarTransmissionAndLocationAndPriceAndRentTime(
            String brand,
            String model,
            Integer maxVelocity,
            Integer fuelConsumption,
            String dimensions,
            Integer weight,
            CarClass carClass,
            CarTransmision carTransmission,
            String location,
            Integer price,
            String rentTime
    );

    List<Vehicule> findByOwner_Id(Long ownerId);
}
