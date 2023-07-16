package com.example.webshop.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "slika", schema = "webshop_ip", catalog = "")
public class SlikaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "slika_proizvoda")
    private String slikaProizvoda;
    @ManyToOne
    @JoinColumn(name = "proizvod_id", referencedColumnName = "id", nullable = false)
    private ProizvodEntity proizvod;

}
