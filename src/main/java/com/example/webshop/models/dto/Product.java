package com.example.webshop.models.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Product {
    private Integer id;
    private String naslov;
    private String opis;
    private Boolean stanje;
    private String kontakt;
    private BigDecimal cijena;
    private String lokacija;
    private Integer zavrsenaPonuda;
    private Date datumKreiranja;
    private List<Comment> komentars;
    private Category kategorija;
    private User prodavac;
    private List<ProductAttribute> proizvodAtributs;
    private List<Image> slikas;
}
