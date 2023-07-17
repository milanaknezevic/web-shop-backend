package com.example.webshop.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccountActivationRequest {

    @NotBlank
    private String code;
    @NotBlank
    private String korisnickoIme;
}