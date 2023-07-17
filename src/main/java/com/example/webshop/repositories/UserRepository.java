package com.example.webshop.repositories;

import com.example.webshop.models.entities.KorisnikEntity;
import com.example.webshop.models.enums.Role;
import com.example.webshop.models.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<KorisnikEntity,Integer> {

    Boolean existsByKorisnickoIme(String korisnickoIme);
    List<KorisnikEntity> findAll();
    List<KorisnikEntity> findAllByRolaAndStatus(Role role, UserStatus status);
    Optional<KorisnikEntity> findByKorisnickoImeAndStatus(String username, UserStatus status);

    Optional<KorisnikEntity> findByKorisnickoIme(String username);

}
