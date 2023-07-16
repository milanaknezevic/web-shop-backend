package com.example.webshop.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "proizvod_atribut")
public class ProizvodAtributEntity {
    @EmbeddedId
    private ProizvodAtributEntityPK id;
    @MapsId("proizvodId")
    @ManyToOne
    @JoinColumn(name = "proizvod_id", referencedColumnName = "id", nullable = false)
    private ProizvodEntity proizvod;
    @MapsId("atributId")
    @ManyToOne
    @JoinColumn(name = "atribut_id", referencedColumnName = "id", nullable = false)
    private AtributEntity atribut;

}
