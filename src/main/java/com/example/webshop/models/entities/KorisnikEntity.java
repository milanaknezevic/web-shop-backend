package com.example.webshop.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "korisnik", schema = "webshop_ip", catalog = "")
public class KorisnikEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "ime")
    private String ime;
    @Basic
    @Column(name = "prezime")
    private String prezime;
    @Basic
    @Column(name = "korisnicko_ime")
    private String korisnickoIme;
    @Basic
    @Column(name = "lozinka")
    private String lozinka;
    @Basic
    @Column(name = "grad")
    private String grad;
    @Basic
    @Column(name = "avatar")
    private String avatar;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "rola")
    private Boolean rola;
    @Basic
    @Column(name = "status")
    private Boolean status;
    @OneToMany(mappedBy = "korisnik_komentar")
    private List<KomentarEntity> komentars;
    @OneToMany(mappedBy = "korisnik")
    private List<PorukaEntity> porukas;
    @OneToMany(mappedBy = "prodavac")
    private List<ProizvodEntity> proizvodsByProdavac;
    @OneToMany(mappedBy = "kupac")
    private List<ProizvodEntity> proizvodsByKupac;

}
