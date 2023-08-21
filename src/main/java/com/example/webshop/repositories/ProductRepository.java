package com.example.webshop.repositories;

import com.example.webshop.models.dto.Product;
import com.example.webshop.models.entities.ProizvodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProizvodEntity, Integer> {

    @Query("SELECT p from ProizvodEntity p where p.zavrsenaPonuda=0 ORDER BY p.datumKreiranja DESC")
    Page<ProizvodEntity> findAllByZavrsenaPonuda(Pageable page);
    @Query("SELECT p FROM ProizvodEntity p WHERE LOWER(p.naslov) LIKE LOWER(CONCAT('%', :naslov, '%')) and p.zavrsenaPonuda=0 ORDER BY p.datumKreiranja DESC")
    Page<ProizvodEntity> findALlByTitleAndZavrsenaPonuda(Pageable page, String naslov);


}
