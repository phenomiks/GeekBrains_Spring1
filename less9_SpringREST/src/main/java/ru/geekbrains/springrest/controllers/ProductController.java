package ru.geekbrains.springrest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springrest.entities.Product;
import ru.geekbrains.springrest.exceptions.ProductNotFoundException;
import ru.geekbrains.springrest.exceptions.ProductsErrorResponse;
import ru.geekbrains.springrest.services.ProductService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Product getProductById(@PathVariable(name = "id") Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new ProductNotFoundException("No product found with id = " + id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProductById(@PathVariable(name = "id") Long id) {
        productService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product product) {
        product.setId(0L);
        return productService.saveOrUpdate(product);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.saveOrUpdate(product);
    }

    @ExceptionHandler
    public ResponseEntity<ProductsErrorResponse> handleException(ProductNotFoundException exc) {
        ProductsErrorResponse productsErrorResponse = new ProductsErrorResponse();
        productsErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        productsErrorResponse.setMessage(exc.getMessage());
        productsErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(productsErrorResponse, HttpStatus.NOT_FOUND);
    }
}
