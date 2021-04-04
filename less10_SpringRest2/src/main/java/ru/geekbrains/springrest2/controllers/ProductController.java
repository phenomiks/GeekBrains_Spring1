package ru.geekbrains.springrest2.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springrest2.entities.Product;
import ru.geekbrains.springrest2.exceptions.ProductNotFoundException;
import ru.geekbrains.springrest2.services.ProductService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ApiOperation("Getting a list of all products")
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("Getting a product by id")
    public Product getProductById(@PathVariable Long id) {
        return productService.findProductById(id).orElseThrow(() -> new ProductNotFoundException
                ("No product found with id = " + id));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("Removing a product by identifier")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Saving a new product")
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveOrUpdate(product);
    }

    @PutMapping
    @ApiOperation("Updating product fields")
    public Product updateProduct(@RequestBody Product product) {
        return productService.saveOrUpdate(product);
    }
}
