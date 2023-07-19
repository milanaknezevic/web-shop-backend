package com.example.webshop.services;

import com.example.webshop.exceptions.NotFoundException;
import com.example.webshop.models.dto.Product;
import com.example.webshop.models.requests.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ProductService {

    Product findById(Integer id) throws NotFoundException;

    Page<Product> getAllProducts(Pageable page, Integer zavrsenaPonuda);

    Product insert(ProductRequest productRequest, Authentication authentication);
}
