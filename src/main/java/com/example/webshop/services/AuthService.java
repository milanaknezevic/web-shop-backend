package com.example.webshop.services;

import com.example.webshop.models.dto.LoginResponse;
import com.example.webshop.models.requests.AccountActivationRequest;
import com.example.webshop.models.requests.LoginRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void sendActivationCode(String username,String mail);

    boolean activateAccount(AccountActivationRequest request);
}