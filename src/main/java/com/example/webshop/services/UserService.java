package com.example.webshop.services;

import com.example.webshop.models.dto.User;
import com.example.webshop.models.requests.SignUpRequest;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Integer id);
    void signUp(SignUpRequest request);
}
