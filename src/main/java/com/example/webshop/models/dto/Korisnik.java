package com.example.webshop.models.dto;

import com.example.webshop.models.enums.Role;
import com.example.webshop.models.enums.UserStatus;
import lombok.Data;

@Data
public class Korisnik {
    private Integer id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private String grad;
    private String avatar;
    private String email;
    private Role rola;
    private UserStatus status;
}
