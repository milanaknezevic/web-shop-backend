package com.example.webshop.models.requests;

import com.example.webshop.models.enums.Role;
import com.example.webshop.models.enums.UserStatus;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SignUpRequest {

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
