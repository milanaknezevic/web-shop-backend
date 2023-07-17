package com.example.webshop.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends User {
    private String token;
}
