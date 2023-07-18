package com.example.webshop.models.dto;

import com.example.webshop.models.entities.AtributEntity;
import lombok.Data;

@Data
public class ProductAttribute {
    private String vrijednost;
    private AtributEntity atribut;
}
