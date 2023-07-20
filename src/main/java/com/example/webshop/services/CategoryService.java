package com.example.webshop.services;

import com.example.webshop.exceptions.NotFoundException;
import com.example.webshop.models.dto.Category;
import com.example.webshop.models.dto.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CategoryService {
     List<Category> findAll();
     Category findById(Integer id) throws NotFoundException;
     Page<Product> findAllProductsInCategory(Pageable page, @PathVariable Integer id);
}
