package com.example.webshop.models.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "proizvod", schema = "webshop_ip", catalog = "")
public class ProizvodEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "naslov")
    private String naslov;
    @Basic
    @Column(name = "opis")
    private String opis;
    @Basic
    @Column(name = "stanje")
    private Boolean stanje;
    @Basic
    @Column(name = "kontakt")
    private String kontakt;
    @Basic
    @Column(name = "cijena")
    private BigDecimal cijena;
    @Basic
    @Column(name = "lokacija")
    private String lokacija;


    @Basic
    @Column(name = "zavrsena_ponuda")
    private Integer zavrsenaPonuda;
    @Basic
    @Column(name = "datum_kreiranja")
    private Date datumKreiranja;
    @OneToMany(mappedBy = "proizvod_komentar")
    private List<KomentarEntity> komentars;
    @ManyToOne
    @JoinColumn(name = "kategorija_id", referencedColumnName = "id", nullable = false)
    private KategorijaEntity kategorija;
    @ManyToOne
    @JoinColumn(name = "prodavac_id", referencedColumnName = "id", nullable = false)
    private KorisnikEntity prodavac;
    @ManyToOne
    @JoinColumn(name = "kupac_id", referencedColumnName = "id", nullable = false)
    private KorisnikEntity kupac;
    @OneToMany(mappedBy = "proizvod")
    private List<ProizvodAtributEntity> proizvodAtributs;
    @OneToMany(mappedBy = "proizvod")
    private List<SlikaEntity> slikas;

}
