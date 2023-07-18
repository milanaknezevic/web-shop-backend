package com.example.webshop.repositories;

import com.example.webshop.models.entities.PorukaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<PorukaEntity,Integer> {
}
