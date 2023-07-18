package com.example.webshop.models.dto;

import lombok.Data;

@Data
public class Message {
    private Integer id;

    private String sadrzaj;

    private Boolean procitana;
    private User korisnik;
}
