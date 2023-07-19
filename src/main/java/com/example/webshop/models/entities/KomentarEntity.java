package com.example.webshop.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "komentar", schema = "webshop_ip", catalog = "")
public class KomentarEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "datum")
    private Date datum;
    @Basic
    @Column(name = "pitanje")
    private String pitanje;
    @Basic
    @Column(name = "odgovor")
    private String odgovor;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "korisnik_id", referencedColumnName = "id", nullable = false)
    private KorisnikEntity korisnik_komentar;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "proizvod_id", referencedColumnName = "id", nullable = false)
    private ProizvodEntity proizvod_komentar;

}
