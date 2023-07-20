package com.example.webshop.repositories;

import com.example.webshop.models.entities.KategorijaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<KategorijaEntity,Integer> {
}
