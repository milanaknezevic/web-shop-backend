package com.example.webshop.repositories;

import com.example.webshop.models.entities.LogerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogerRepository  extends JpaRepository<LogerEntity,Integer> {
}
