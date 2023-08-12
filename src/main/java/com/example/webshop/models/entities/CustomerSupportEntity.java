package com.example.webshop.models.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "customer_support", schema = "webshop_ip", catalog = "")
public class CustomerSupportEntity {
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
}
