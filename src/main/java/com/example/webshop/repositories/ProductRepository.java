package com.example.webshop.repositories;

import com.example.webshop.models.dto.Product;
import com.example.webshop.models.entities.ProizvodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProizvodEntity, Integer> {
    //Page<Product> findAllByZavrsenaPonuda(Pageable page);
    @Query("SELECT p from ProizvodEntity p where p.zavrsenaPonuda=0")
    Page<ProizvodEntity> findAllByZavrsenaPonuda(Pageable page);
}
