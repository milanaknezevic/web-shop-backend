package com.example.webshop.repositories;

import com.example.webshop.models.entities.KorisnikEntity;
import com.example.webshop.models.entities.ProizvodEntity;
import com.example.webshop.models.enums.Role;
import com.example.webshop.models.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<KorisnikEntity,Integer> {

    Boolean existsByKorisnickoIme(String korisnickoIme);
    List<KorisnikEntity> findAll();
    List<KorisnikEntity> findAllByRolaAndStatus(Role role, UserStatus status);
    Optional<KorisnikEntity> findByKorisnickoImeAndStatus(String username, UserStatus status);

    Optional<KorisnikEntity> findByKorisnickoIme(String username);
    @Query("SELECT p FROM ProizvodEntity p WHERE p.kupac.id=:id")
    Page<ProizvodEntity> getAllProductsForBuyer(Pageable page, Integer id);

    @Query("SELECT p FROM ProizvodEntity p WHERE p.prodavac.id=:id and p.zavrsenaPonuda=:finished")
    Page<ProizvodEntity> getAllProductsForSeller(Pageable page,Integer id,Integer finished);



}
