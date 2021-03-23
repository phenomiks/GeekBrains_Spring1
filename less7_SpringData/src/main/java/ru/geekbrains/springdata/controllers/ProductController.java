package ru.geekbrains.springdata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springdata.entities.Product;
import ru.geekbrains.springdata.services.ProductService;

import java.util.List;

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
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/find_by_price")
    public List<Product> getProductsGreater(@RequestParam(required = false, defaultValue = "0") int min,
                                            @RequestParam(required = false, defaultValue = "0") int max) {
        if (min == 0 && max == 0) {
            return getAllProducts();
        } else if (max == 0) {
            return productService.getProductsWithPriceGreaterValue(min);
        } else if (min == 0) {
            return productService.getProductsWithPriceLessValue(max);
        }
        return productService.getProductsWithPriceBetween(min, max);
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        Product deletedProduct = productService.deleteById(id);
        return String.format("Removed the following product:%n" +
                "title: %s, price: %d", deletedProduct.getTitle(), deletedProduct.getPrice());
    }

    @PostMapping
    public String saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return String.format("Next product saved:%n" +
                "title: %s, price: %d", product.getTitle(), product.getPrice());
    }
}
