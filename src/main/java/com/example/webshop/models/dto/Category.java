package com.example.webshop.models.dto;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;

@Data
public class Category {
    private Integer id;
    private String naziv;
}
