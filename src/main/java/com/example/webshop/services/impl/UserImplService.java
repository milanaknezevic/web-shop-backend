package com.example.webshop.services.impl;

import com.example.webshop.exceptions.BadRequestException;
import com.example.webshop.exceptions.ConflictException;
import com.example.webshop.exceptions.NotFoundException;
import com.example.webshop.models.dto.JwtUser;
import com.example.webshop.models.dto.Product;
import com.example.webshop.models.dto.User;
import com.example.webshop.models.entities.AdminEntity;
import com.example.webshop.models.entities.CustomerSupportEntity;
import com.example.webshop.models.entities.KorisnikEntity;
import com.example.webshop.models.enums.UserStatus;
import com.example.webshop.models.requests.ChangePasswordRequest;
import com.example.webshop.models.requests.SignUpRequest;
import com.example.webshop.models.requests.UserUpdateRequest;
import com.example.webshop.repositories.AdminRepository;
import com.example.webshop.repositories.CustomerSupportRepositoy;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserImplService implements UserService {

    public final UserRepository userRepository;
    public final AdminRepository adminRepository;
    public final CustomerSupportRepositoy customerSupportRepositoy;

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


    @Value("${authorization.default.support-username}")
    private String defaultSupportUsername;
    @Value("${authorization.default.support-password:}")
    private String defaultSupportPassword;
    @Value("${authorization.default.support-first-name:}")
    private String defaultSupportFirstName;
    @Value("${authorization.default.support-last-name:}")
    private String defaultSupportLastName;

    @Value("${avatarDir:}")
    private String avatarDir;
    @Value("${productDir:}")
    private String productsDir;
    @PersistenceContext
    private EntityManager manager;

    public UserImplService(UserRepository userRepository, AdminRepository adminRepository, CustomerSupportRepositoy customerSupportRepositoy, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthService authService, LogerService logerService) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.customerSupportRepositoy = customerSupportRepositoy;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;

        this.authService = authService;
        this.logerService = logerService;
    }

    @PostConstruct
    public void postConstruct() {

        if (adminRepository.count() == 0) {
            AdminEntity admin = new AdminEntity();
            //admin
            admin.setIme(defaultFirstName);
            admin.setPrezime(defaultLastName);
            admin.setKorisnickoIme(defaultUsername);
            admin.setLozinka(passwordEncoder.encode(defaultPassword));
            adminRepository.saveAndFlush(admin);
        }
        if (customerSupportRepositoy.count() == 0) {

            CustomerSupportEntity customer_support = new CustomerSupportEntity();
            customer_support.setIme(defaultSupportFirstName);
            customer_support.setPrezime(defaultSupportLastName);
            customer_support.setKorisnickoIme(defaultSupportUsername);
            customer_support.setLozinka(passwordEncoder.encode(defaultSupportPassword));


            customerSupportRepositoy.saveAndFlush(customer_support);
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().map(l -> modelMapper.map(l, User.class)).collect(Collectors.toList());
    }

    @Override
    public List<String> insertImages(List<MultipartFile> files) {
        List<String> productImages = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : files) {
                String imageName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
                Path imagePath = Paths.get(productsDir + imageName);
                Files.copy(multipartFile.getInputStream(), imagePath);
                productImages.add(imageName);
            }
            return productImages;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }


    @Override
    public User findById(Integer id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(NotFoundException::new), User.class);
    }

    @Override
    public void signUp(SignUpRequest request) {
        if (userRepository.existsByKorisnickoIme(request.getKorisnickoIme()) || userRepository.existsByEmail(request.getEmail()))
            throw new ConflictException();
        KorisnikEntity entity = modelMapper.map(request, KorisnikEntity.class);
        entity.setLozinka(passwordEncoder.encode(entity.getLozinka()));
        entity.setStatus(UserStatus.REQUESTED);
        // entity.setRola(Role.OBICNI_KORISNIK);

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
    public String insertImage(MultipartFile multipartFile) {
        try {
            String name = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
            Path path = Paths.get(avatarDir + name);
            Files.copy(multipartFile.getInputStream(), path);
            System.out.println("path" + path);
            return name;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<Product> getAllProductsForBuyer(Pageable page, Authentication authentication) {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        logerService.insertLog("User: " + user.getUsername() + " has searched his purchased products.", this.getClass().getName());
        return userRepository.getAllProductsForBuyer(page, user.getId()).map(p -> modelMapper.map(p, Product.class));
    }

    @Override
    public Page<Product> getAllProductsForSeller(Pageable page, Authentication authentication, Integer finished) {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        logerService.insertLog("User: " + user.getUsername() + " has searched his sold products.", this.getClass().getName());

        System.out.println("finished" + finished);
        if (finished != null) {
            return userRepository.getAllProductsForSeller(page, user.getId(), finished).map(p -> modelMapper.map(p, Product.class));

        } else {
            return userRepository.getAllProductsForSellerWithoutFinished(page, user.getId()).map(p -> modelMapper.map(p, Product.class));

        }

    }

    @Override
    public User update(Integer id, UserUpdateRequest userRequest) throws Exception {
        KorisnikEntity korisnikEntity = userRepository.findById(id).orElseThrow(NotFoundException::new);
        korisnikEntity.setIme(userRequest.getIme());
        korisnikEntity.setPrezime(userRequest.getPrezime());
        ;
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
