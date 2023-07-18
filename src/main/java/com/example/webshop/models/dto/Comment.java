package com.example.webshop.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Integer id;
    private Date datum;
    private String pitanje;
    private String odgovor;
   private User korisnik_komentar;
   // private Product proizvod_komentar;

}
