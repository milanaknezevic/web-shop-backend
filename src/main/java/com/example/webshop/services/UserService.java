package com.example.webshop.services;

import com.example.webshop.models.dto.Product;
import com.example.webshop.models.dto.User;
import com.example.webshop.models.requests.ChangePasswordRequest;
import com.example.webshop.models.requests.SignUpRequest;
import com.example.webshop.models.requests.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Integer id);

    void signUp(SignUpRequest request);

    Page<Product> getAllProductsForBuyer(Pageable page, Authentication authentication);

    Page<Product> getAllProductsForSeller(Pageable page, Authentication authentication, Integer finished);

    User update(Integer id, UserUpdateRequest user) throws Exception;

    User updatePassword(Integer id, ChangePasswordRequest changePasswordRequest);
}
