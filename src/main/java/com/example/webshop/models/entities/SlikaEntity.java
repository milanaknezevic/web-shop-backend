package com.example.webshop.models.entities;

import lombok.*;

import javax.persistence.*;

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
    @ToString.Exclude
    @JoinColumn(name = "proizvod_id", referencedColumnName = "id", nullable = false)
    private ProizvodEntity proizvod;

}
