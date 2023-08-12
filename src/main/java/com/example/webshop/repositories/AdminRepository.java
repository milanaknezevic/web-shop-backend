package com.example.webshop.repositories;

import com.example.webshop.models.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface AdminRepository extends JpaRepository< AdminEntity, CriteriaBuilder.In> {
}
