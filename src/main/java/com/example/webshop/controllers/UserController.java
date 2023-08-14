package com.example.webshop.controllers;

import com.example.webshop.exceptions.ForbiddenException;
import com.example.webshop.models.dto.JwtUser;
import com.example.webshop.models.dto.Product;
import com.example.webshop.models.dto.User;
import com.example.webshop.models.requests.ChangePasswordRequest;
import com.example.webshop.models.requests.UserUpdateRequest;
import com.example.webshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping("/purchases") //kuonje jednog kupca
    public Page<Product> getAllProductsForBuyer(Pageable page, Authentication authentication) {
        return userService.getAllProductsForBuyer(page, authentication);
    }

    @GetMapping("/products") //prodaje jednog prodavca/proizvodi koji su prodani ili koje on treba da proda
    public Page<Product> getAllProductsForSeller(Pageable page, Authentication authentication, @RequestParam(required = false) Integer finished) {
        return userService.getAllProductsForSeller(page, authentication, finished);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest request, Authentication auth) throws Exception {
        JwtUser user = (JwtUser) auth.getPrincipal();
        if (!user.getId().equals(id)) {
            throw new ForbiddenException();
        }
        return userService.update(id, request);
    }

    @PutMapping("/{id}/change-password")
    public User changePassword(@PathVariable Integer id, @Valid @RequestBody ChangePasswordRequest request, Authentication authentication) throws Exception {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        if (!user.getId().equals(id)) {
            throw new ForbiddenException();
        }
        return userService.updatePassword(id, request);
    }

}
