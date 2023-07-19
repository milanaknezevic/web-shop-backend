package com.example.webshop.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductAtributeRequest {
    @NotBlank
    private String vrijednost;
    @NotNull
    private Integer attributId;
}
