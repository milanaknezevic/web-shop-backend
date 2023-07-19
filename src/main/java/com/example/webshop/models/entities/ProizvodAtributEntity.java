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
    @ToString.Exclude
    @JoinColumn(name = "proizvod_id", referencedColumnName = "id", nullable = false)
    private ProizvodEntity proizvod;
    @MapsId("atributId")
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "atribut_id", referencedColumnName = "id", nullable = false)
    private AtributEntity atribut;
    @Basic
    @Column(name = "vrijednost", nullable = false, length = 45)
    private String vrijednost;

}
