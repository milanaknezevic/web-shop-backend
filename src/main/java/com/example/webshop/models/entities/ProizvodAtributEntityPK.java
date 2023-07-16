package com.example.webshop.models.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ProizvodAtributEntityPK implements Serializable {
    @Column(name = "proizvod_id")
    private Integer proizvodId;
    @Column(name = "atribut_id")
    private Integer atributId;

}
