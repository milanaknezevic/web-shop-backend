package com.example.webshop.services.impl;

import com.example.webshop.models.dto.Korisnik;
import com.example.webshop.models.entities.KorisnikEntity;
import com.example.webshop.models.enums.Role;
import com.example.webshop.models.enums.UserStatus;
import com.example.webshop.repositories.KorisnikRepository;
import com.example.webshop.services.KorisnikService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class KorisnikImplService implements KorisnikService {

    public final KorisnikRepository korisnikRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Value("${authorization.default.first-name:}")
    private String defaultFirstName;
    @Value("${authorization.default.last-name:}")
    private String defaultLastName;
    @Value("${authorization.default.username:}")
    private String defaultUsername;
    @Value("${authorization.default.password:}")
    private String defaultPassword;
    @Value("${authorization.default.city:}")
    private String defaultCity;
    @Value("${authorization.default.email:}")
    private String defaultEmail;

    public KorisnikImplService(KorisnikRepository korisnikRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.korisnikRepository = korisnikRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    @PersistenceContext
    private EntityManager manager;
    @PostConstruct
    public void postConstruct() {

        if (korisnikRepository.count() == 0) {
            KorisnikEntity userEntity = new KorisnikEntity();
            userEntity.setIme(defaultFirstName);
            userEntity.setPrezime(defaultLastName);
            userEntity.setKorisnickoIme(defaultUsername);
            userEntity.setLozinka(passwordEncoder.encode(defaultPassword));
            userEntity.setEmail(defaultEmail);
            userEntity.setGrad(defaultCity);
            userEntity.setStatus(UserStatus.ACTIVE);
            userEntity.setRola(Role.ADMIN);
            korisnikRepository.saveAndFlush(userEntity);
        }
    }

    @Override
    public List<Korisnik> findAll() {
        return korisnikRepository.findAll().stream().map(l->modelMapper.map(l,Korisnik.class)).collect(Collectors.toList());
    }
}
