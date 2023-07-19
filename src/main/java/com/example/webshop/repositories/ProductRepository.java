package com.example.webshop.repositories;

import com.example.webshop.models.dto.Product;
import com.example.webshop.models.entities.ProizvodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProizvodEntity, Integer> {
    Page<Product> findAllByZavrsenaPonuda(Pageable page,Integer zavrsenaPonuda);
}
