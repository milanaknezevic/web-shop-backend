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


    @PersistenceContext
    private EntityManager manager;

    public UserImplService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthService authService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;

        this.authService = authService;
    }

    @PostConstruct
    public void postConstruct() {

        if (userRepository.count() == 0) {
            KorisnikEntity userEntity = new KorisnikEntity();
            userEntity.setIme(defaultFirstName);
            userEntity.setPrezime(defaultLastName);
            userEntity.setKorisnickoIme(defaultUsername);
            userEntity.setLozinka(passwordEncoder.encode(defaultPassword));
            userEntity.setEmail(defaultEmail);
            userEntity.setGrad(defaultCity);
            userEntity.setStatus(UserStatus.ACTIVE);
            userEntity.setRola(Role.ADMIN);
            userRepository.saveAndFlush(userEntity);
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
        if (userRepository.existsByKorisnickoIme(request.getKorisnickoIme()))
            throw new ConflictException();
        KorisnikEntity entity = modelMapper.map(request, KorisnikEntity.class);
        entity.setLozinka(passwordEncoder.encode(entity.getLozinka()));
        entity.setStatus(UserStatus.REQUESTED);
        entity.setRola(Role.OBICNI_KORISNIK);

        entity = userRepository.saveAndFlush(entity);
        authService.sendActivationCode(entity.getKorisnickoIme(), entity.getEmail());
        // User user = insert(entity, Korisnik.class);
    }

    @Override
    public Page<Product> getAllProductsForBuyer(Pageable page, Authentication authentication) {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        return userRepository.getAllProductsForBuyer(page, user.getId()).map(p -> modelMapper.map(p, Product.class));
    }

    @Override
    public Page<Product> getAllProductsForSeller(Pageable page, Authentication authentication,Integer finished) {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        return userRepository.getAllProductsForSeller(page, user.getId(),finished).map(p -> modelMapper.map(p, Product.class));

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
        return modelMapper.map(userRepository.saveAndFlush(korisnikEntity), User.class);
    }

    @Override
    public User updatePassword(Integer id, ChangePasswordRequest changePasswordRequest) {
        KorisnikEntity korisnikEntity=userRepository.findById(id).orElseThrow(NotFoundException::new);
        if(changePasswordRequest.getLozinka() == changePasswordRequest.getNewPassword() &&
                passwordEncoder.matches(korisnikEntity.getLozinka(),changePasswordRequest.getOldPassword()))
        {
            korisnikEntity.setLozinka(passwordEncoder.encode(changePasswordRequest.getLozinka()));
            return modelMapper.map(userRepository.saveAndFlush(korisnikEntity),User.class);
        }
        else
        {
            throw new BadRequestException();
        }
    }
}
