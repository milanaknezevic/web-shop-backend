package com.example.webshop.repositories;

import com.example.webshop.models.entities.ProizvodAtributEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface ProductAtributeRepository extends JpaRepository<ProizvodAtributEntity, Integer> {
}
