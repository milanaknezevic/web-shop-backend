package com.example.webshop.services.impl;

import com.example.webshop.exceptions.BadRequestException;
import com.example.webshop.exceptions.ConflictException;
import com.example.webshop.exceptions.NotFoundException;
import com.example.webshop.models.dto.JwtUser;
import com.example.webshop.models.dto.Product;
import com.example.webshop.models.dto.User;
import com.example.webshop.models.entities.KorisnikEntity;
import com.example.webshop.models.enums.Role;
import com.example.webshop.models.enums.UserStatus;
import com.example.webshop.models.requests.ChangePasswordRequest;
import com.example.webshop.models.requests.SignUpRequest;
import com.example.webshop.models.requests.UserUpdateRequest;
import com.example.webshop.repositories.UserRepository;
import com.example.webshop.services.AuthService;
import com.example.webshop.services.LogerService;
import com.example.webshop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserImplService implements UserService {

    public final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final LogerService logerService;

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

    @Value("${authorization.default.support-username}")
    private String defaultSupportUsername;
    @Value("${authorization.default.support-password:}")
    private String defaultSupportPassword;
    @Value("${authorization.default.support-first-name:}")
    private String defaultSupportFirstName;
    @Value("${authorization.default.support-last-name:}")
    private String defaultSupportLastName;
    @Value("${authorization.default.support-city:}")
    private String defaultSupportCity;
    @Value("${authorization.default.support-email:}")
    private String defaultSupportEmail;

    @PersistenceContext
    private EntityManager manager;

    public UserImplService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthService authService, LogerService logerService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;

        this.authService = authService;
        this.logerService = logerService;
    }

    @PostConstruct
    public void postConstruct() {

        if (userRepository.count() == 0) {
            KorisnikEntity admin = new KorisnikEntity();
            //admin
            admin.setIme(defaultFirstName);
            admin.setPrezime(defaultLastName);
            admin.setKorisnickoIme(defaultUsername);
            admin.setLozinka(passwordEncoder.encode(defaultPassword));
            admin.setEmail(defaultEmail);
            admin.setGrad(defaultCity);
            admin.setStatus(UserStatus.ACTIVE);
            admin.setRola(Role.ADMIN);
            //customer_support
            KorisnikEntity customer_support=new KorisnikEntity();
            customer_support.setIme(defaultSupportFirstName);
            customer_support.setPrezime(defaultSupportLastName);
            customer_support.setKorisnickoIme(defaultSupportUsername);
            customer_support.setLozinka(passwordEncoder.encode(defaultSupportPassword));
            customer_support.setEmail(defaultSupportEmail);
            customer_support.setGrad(defaultSupportCity);
            customer_support.setStatus(UserStatus.ACTIVE);
            customer_support.setRola(Role.KORISNICKA_PODRSKA);

            userRepository.saveAndFlush(admin);
            userRepository.saveAndFlush(customer_support);
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().map(l -> modelMapper.map(l, User.class)).collect(Collectors.toList());
    }


    @Override
    public User findById(Integer id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(NotFoundException::new), User.class);
    }

    @Override
    public void signUp(SignUpRequest request) {
        if (userRepository.existsByKorisnickoIme(request.getKorisnickoIme()) && userRepository.existsByEmail(request.getEmail()))
            throw new ConflictException();
        KorisnikEntity entity = modelMapper.map(request, KorisnikEntity.class);
        entity.setLozinka(passwordEncoder.encode(entity.getLozinka()));
        entity.setStatus(UserStatus.REQUESTED);
        entity.setRola(Role.OBICNI_KORISNIK);

        entity = userRepository.saveAndFlush(entity);
        logerService.insertLog("New user: " + entity.getKorisnickoIme() + " has registered.", this.getClass().getName());

        authService.sendActivationCode(entity.getKorisnickoIme(), entity.getEmail());

    }

    @Override
    public User activateAccount(String username) {
        KorisnikEntity userEntity = userRepository.findByKorisnickoImeAndStatus(username, UserStatus.REQUESTED).orElseThrow(NotFoundException::new);
        userEntity.setStatus(UserStatus.ACTIVE);
        logerService.insertLog("User: " + userEntity.getKorisnickoIme() + " has activated profile.", this.getClass().getName());
        return modelMapper.map(userRepository.saveAndFlush(userEntity), User.class);
    }

    @Override
    public Page<Product> getAllProductsForBuyer(Pageable page, Authentication authentication) {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        logerService.insertLog("User: " + user.getUsername() + " has searched his purchased products.", this.getClass().getName());
/*  if (title == null || title.isEmpty()) {
            return userRepository.getAllProductsForBuyer(page, user.getId()).map(p -> modelMapper.map(p, Product.class));
        } else {
            return userRepository.getAllProductsForBuyerAndSearch(page, user.getId(), title).map(p -> modelMapper.map(p, Product.class));
        }*/
        return userRepository.getAllProductsForBuyer(page, user.getId()).map(p -> modelMapper.map(p, Product.class));
    }

    @Override
    public Page<Product> getAllProductsForSeller(Pageable page, Authentication authentication, Integer finished) {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        logerService.insertLog("User: " + user.getUsername() + " has searched his sold products.", this.getClass().getName());
/* if (title == null || title.isEmpty()) {
            return userRepository.getAllProductsForSeller(page, user.getId(), finished).map(p -> modelMapper.map(p, Product.class));
        } else {
            return userRepository.getAllProductsForSellerSearch(page, user.getId(),finished,title).map(p -> modelMapper.map(p, Product.class));

        }*/
        return userRepository.getAllProductsForSeller(page, user.getId(), finished).map(p -> modelMapper.map(p, Product.class));

    }

    @Override
    public User update(Integer id, UserUpdateRequest userRequest) throws Exception {
        KorisnikEntity korisnikEntity = userRepository.findById(id).orElseThrow(NotFoundException::new);
        korisnikEntity.setIme(userRequest.getIme());
        korisnikEntity.setPrezime(userRequest.getPrezime());
        korisnikEntity.setKorisnickoIme(userRequest.getKorisnickoIme());
        korisnikEntity.setGrad(userRequest.getGrad());
        korisnikEntity.setAvatar(userRequest.getAvatar());
        korisnikEntity.setEmail(userRequest.getEmail());
        logerService.insertLog("User: " + korisnikEntity.getKorisnickoIme() + " has updated profile.", this.getClass().getName());

        return modelMapper.map(userRepository.saveAndFlush(korisnikEntity), User.class);
    }

    @Override
    public User updatePassword(Integer id, ChangePasswordRequest changePasswordRequest) {
        KorisnikEntity korisnikEntity = userRepository.findById(id).orElseThrow(NotFoundException::new);
        if (changePasswordRequest.getLozinka().equals(changePasswordRequest.getNewPassword())) {
            korisnikEntity.setLozinka(passwordEncoder.encode(changePasswordRequest.getLozinka()));
            logerService.insertLog("User: " + korisnikEntity.getKorisnickoIme() + " has changed password.", this.getClass().getName());
            return modelMapper.map(userRepository.saveAndFlush(korisnikEntity), User.class);
        } else {
            throw new BadRequestException();
        }
    }
}
