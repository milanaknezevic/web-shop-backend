package com.example.webshop.services.impl;

import com.example.webshop.exceptions.NotFoundException;
import com.example.webshop.models.dto.Category;
import com.example.webshop.models.dto.Product;
import com.example.webshop.models.entities.KategorijaEntity;
import com.example.webshop.repositories.CategoryRepository;
import com.example.webshop.services.CategoryService;
import com.example.webshop.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryImplService implements CategoryService {
    private final CategoryRepository categoryRepository;
    //   private final LoggerService loggerService;
    private final ModelMapper modelMapper;

    @Override
    public List<Category> findAll() {
        //loggerService.saveLog("Get all categories", this.getClass().getName());
        return categoryRepository.findAll().stream().map(c -> modelMapper.map(c, Category.class)).collect(Collectors.toList());
    }

    @Override
    public Category findById(Integer id) throws NotFoundException {
        return modelMapper.map(categoryRepository.findById(id).orElseThrow(NotFoundException::new), Category.class);
    }

    @Override
    public Page<Product> findAllProductsInCategory(Pageable page, Integer id) {
        KategorijaEntity kategorijaEntity=categoryRepository.findById(id).orElseThrow(NotFoundException::new);
        List<Product> productsInCategory = kategorijaEntity.getProizvod().stream().map(p -> modelMapper.map(p, Product.class)).collect(Collectors.toList());
        //loggerService.saveLog("Get all products in given category ", this.getClass().getName());
        return Util.getPage(page, productsInCategory);
    }
}
