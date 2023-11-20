package com.autoya.autoya_api.autoya.domain.model.entities;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Contract;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Images;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing a vehicle owner.
 */
@Entity
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false ,unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String password;

    @Column(nullable = false)
     private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String birthDate;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "owner")
    private List<Vehicule> vehicles;

    @OneToMany(mappedBy = "owner")
    private List<Contract> contracts;

    @OneToOne(mappedBy = "owner")
    private Images images;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
