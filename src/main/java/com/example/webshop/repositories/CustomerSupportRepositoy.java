package com.example.webshop.repositories;

import com.example.webshop.models.entities.AdminEntity;
import com.example.webshop.models.entities.CustomerSupportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSupportRepositoy extends JpaRepository<CustomerSupportEntity,Integer> {
}
