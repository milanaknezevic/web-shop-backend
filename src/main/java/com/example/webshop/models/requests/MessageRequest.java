package com.example.webshop.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MessageRequest {
    @NotBlank
    private String sadrzaj;
}
