package com.example.webshop.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "kategorija", schema = "webshop_ip", catalog = "")
public class KategorijaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(mappedBy = "kategorija")
    private List<AtributEntity> atribut;
    @OneToMany(mappedBy = "kategorija")
    private List<ProizvodEntity> proizvod;

}
