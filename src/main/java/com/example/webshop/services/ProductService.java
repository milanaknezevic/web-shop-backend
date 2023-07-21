package com.example.webshop.services;

import com.example.webshop.exceptions.NotFoundException;
import com.example.webshop.models.dto.Comment;
import com.example.webshop.models.dto.Product;
import com.example.webshop.models.requests.AnswerRequest;
import com.example.webshop.models.requests.ProductRequest;
import com.example.webshop.models.requests.QuestionRequest;
import com.example.webshop.models.requests.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ProductService {

    Product findById(Integer id) throws NotFoundException;

    Page<Product> getAllProducts(Pageable page, Integer zavrsenaPonuda);

    Product insert(ProductRequest productRequest, Authentication authentication);
    public void delete(Integer id);
    Comment sendQuestion(Integer id, QuestionRequest questionRequest, Authentication authentication);
    Comment sendAnswer(Integer id, AnswerRequest answerRequest);
    public Product purchaseProduct( Integer id,Authentication authentication);
    Page<Product> searchProducts(Pageable page, SearchRequest searchRequest);


    }
