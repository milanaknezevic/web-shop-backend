package com.example.webshop.models.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest {

    @NotBlank
    private String ime;
    @NotBlank
    private String prezime;
    @NotBlank
    private String korisnickoIme;
    @NotBlank
    private String lozinka;
    @NotBlank
    private String grad;
    private String avatar;
    @NotBlank
    @Email
    private String email;

}
