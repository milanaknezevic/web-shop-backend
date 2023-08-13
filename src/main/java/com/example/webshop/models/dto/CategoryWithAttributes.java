package com.example.webshop.models.dto;
import lombok.Data;

import java.util.List;

@Data
public class CategoryWithAttributes extends Category{
    private List<Atribut> atribut;
}
