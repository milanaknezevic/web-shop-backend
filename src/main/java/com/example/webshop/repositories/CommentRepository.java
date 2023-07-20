package com.example.webshop.repositories;

import com.example.webshop.models.entities.KomentarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<KomentarEntity,Integer> {
}
