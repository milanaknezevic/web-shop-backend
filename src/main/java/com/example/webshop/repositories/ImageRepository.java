package com.example.webshop.repositories;

import com.example.webshop.models.entities.SlikaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<SlikaEntity,Integer> {
}
