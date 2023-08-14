package com.example.webshop.controllers;

import com.example.webshop.models.dto.Comment;
import com.example.webshop.models.dto.Product;
import com.example.webshop.models.requests.AnswerRequest;
import com.example.webshop.models.requests.ProductRequest;
import com.example.webshop.models.requests.QuestionRequest;
import com.example.webshop.models.requests.SearchRequest;
import com.example.webshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public Product findById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @GetMapping
    public Page<Product> getAllProducts(Pageable page,@RequestParam(required = false) String naslov) {
        return productService.getAllProducts(page,naslov);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product insert(@RequestBody @Valid ProductRequest productRequest, Authentication authentication) {
        return productService.insert(productRequest, authentication);
    }

    @PostMapping("/{id}/question")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment sendQuestion(@PathVariable Integer id, @RequestBody @Valid QuestionRequest questionRequest, Authentication authentication) {
        return productService.sendQuestion(id, questionRequest, authentication);
    }

    @PutMapping("/{id}/answer")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment sendAnswer(@PathVariable Integer id, @RequestBody @Valid AnswerRequest answerRequest) {
        return productService.sendAnswer(id, answerRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public Product purchaseProduct(@PathVariable Integer id, Authentication authentication) {
        return productService.purchaseProduct(id, authentication);
    }

    @PostMapping("/searchProducts")
    public Page<Product> searchProducts(Pageable page, @RequestBody SearchRequest searchRequest) {
        return productService.searchProducts(page, searchRequest);
    }
}
