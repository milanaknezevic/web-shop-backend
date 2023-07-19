package com.example.webshop.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "poruka", schema = "webshop_ip", catalog = "")
public class PorukaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "sadrzaj")
    private String sadrzaj;
    @Basic
    @Column(name = "procitana")
    private Boolean procitana;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "korisnik_id", referencedColumnName = "id", nullable = false)
    private KorisnikEntity korisnik;

}
