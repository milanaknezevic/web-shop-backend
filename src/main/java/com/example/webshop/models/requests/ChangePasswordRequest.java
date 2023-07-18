package com.example.webshop.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {
    @NotBlank
    private String lozinka;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
