package com.autoya.autoya_api.autoya.domain.model.aggregate;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Maintenance {
   //GET //devuelve una lista de  todos los nombres de los owner a quienes se les ha alquilado un vehiculo
    //POST envia datos como
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant; // Relación con el arrendatario


    private String problemType;

    @Lob
    private String description;

    private String imageUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
