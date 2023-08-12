package com.example.webshop.models.entities;

import com.example.webshop.models.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private UserStatus status;
    @OneToMany(mappedBy = "korisnik_komentar")
    @ToString.Exclude
    private List<KomentarEntity> komentars;
    @OneToMany(mappedBy = "korisnik")
    @ToString.Exclude
    private List<PorukaEntity> porukas;
    @OneToMany(mappedBy = "prodavac")
    @ToString.Exclude
    private List<ProizvodEntity> proizvodsByProdavac;
    @OneToMany(mappedBy = "kupac")
    @ToString.Exclude
    private List<ProizvodEntity> proizvodsByKupac;

}
