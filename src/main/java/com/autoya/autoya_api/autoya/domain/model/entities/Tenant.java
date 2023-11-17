package com.autoya.autoya_api.autoya.domain.model.entities;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Images;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Rent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
public class Tenant {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,unique = true)
    String email;

    @Column(nullable = false,unique = true)
    String password;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String birthDate;

    @Column(nullable = false,unique = true)
    String phoneNumber;


    @OneToMany(mappedBy = "tenant")
    private List<Vehicule> vehicles;

    @OneToMany(mappedBy = "tenant")
    private List<Rent> rentals;

    @OneToOne(mappedBy = "tenant")
    private Images images;
    public String getFullName() {
        return firstName + " " + lastName;
    }

}
