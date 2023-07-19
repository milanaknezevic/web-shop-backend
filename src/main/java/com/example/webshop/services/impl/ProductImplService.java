package com.example.webshop.services.impl;

import com.example.webshop.exceptions.NotFoundException;
import com.example.webshop.models.dto.JwtUser;
import com.example.webshop.models.dto.Product;
import com.example.webshop.models.entities.*;
import com.example.webshop.repositories.AtributeRepository;
import com.example.webshop.models.requests.ImageRequest;
import com.example.webshop.models.requests.ProductAtributeRequest;
import com.example.webshop.models.requests.ProductRequest;
import com.example.webshop.repositories.ImageRepository;
import com.example.webshop.repositories.ProductAtributeRepository;
import com.example.webshop.repositories.ProductRepository;
import com.example.webshop.repositories.UserRepository;
import com.example.webshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class ProductImplService implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final AtributeRepository atributeRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ProductAtributeRepository productAtributeRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public ProductImplService(ModelMapper modelMapper, ProductRepository productRepository,
                              AtributeRepository atributeRepository, UserRepository userRepository, ImageRepository imageRepository, ProductAtributeRepository productAtributeRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.atributeRepository = atributeRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.productAtributeRepository = productAtributeRepository;
    }

    @Override
    public Product findById(Integer id) throws NotFoundException {
        return modelMapper.map(productRepository.findById(id).orElseThrow(NotFoundException::new), Product.class);
    }

    @Override
    public Page<Product> getAllProducts(Pageable page, Integer zavrsenaPonuda) {
        return productRepository.findAllByZavrsenaPonuda(page, zavrsenaPonuda);
    }

    @Override
    public Product insert(ProductRequest productRequest, Authentication authentication) {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        KorisnikEntity korisnikEntity = userRepository.findById(user.getId()).orElseThrow(NotFoundException::new);
        ProizvodEntity proizvodEntity = modelMapper.map(productRequest, ProizvodEntity.class);
        proizvodEntity.setId(null);
        proizvodEntity.setDatumKreiranja(new Date());
        proizvodEntity.setZavrsenaPonuda(0);
        proizvodEntity.setProdavac(korisnikEntity);
        proizvodEntity = productRepository.saveAndFlush(proizvodEntity);

        for (ImageRequest imageRequest : productRequest.getSlikas()) {
            SlikaEntity slikaEntity = modelMapper.map(imageRequest, SlikaEntity.class);
            slikaEntity.setProizvod(proizvodEntity);
            imageRepository.saveAndFlush(slikaEntity);
        }
        for (ProductAtributeRequest productAtributeRequest : productRequest.getProizvodAtributs()) {
            ProizvodAtributEntityPK proizvodAtributEntityPK = new ProizvodAtributEntityPK();
            proizvodAtributEntityPK.setProizvodId(proizvodEntity.getId());
            proizvodAtributEntityPK.setAtributId(productAtributeRequest.getAttributId());

            ProizvodAtributEntity proizvodAtributEntity = new ProizvodAtributEntity();
            proizvodAtributEntity.setId(proizvodAtributEntityPK);

            AtributEntity atribut = atributeRepository.findById(productAtributeRequest.getAttributId()).orElseThrow(NotFoundException::new);

            proizvodAtributEntity.setProizvod(proizvodEntity);
            proizvodAtributEntity.setAtribut(atribut);
            proizvodAtributEntity.setVrijednost(productAtributeRequest.getVrijednost());
            System.out.println("ciao");
            productAtributeRepository.saveAndFlush(proizvodAtributEntity);
        }
        Product p=modelMapper.map(proizvodEntity, Product.class);
        return p;
    }


}
