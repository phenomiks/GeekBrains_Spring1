package ru.geekbrains.springsecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springsecurity.entities.Product;
import ru.geekbrains.springsecurity.exceptions.ProductNotFoundException;
import ru.geekbrains.springsecurity.services.ProductService;

@Controller
@RequestMapping(value = "/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String findAllProducts(Model model) {
        model.addAttribute("allProducts", productService.findAll());
        return "index";
    }

    @GetMapping(value = "/{id}")
    public String findProductById(@PathVariable Long id, Model model) {
        model.addAttribute("allProducts", productService.findById(id).orElseThrow(() -> new ProductNotFoundException
                ("No product found with id = " + id)));
        return "index";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/api/v1/products";
    }

    @PostMapping
    public String saveProduct(@RequestParam String title,
                            @RequestParam int price) {
        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        productService.saveOrUpdate(product);
        return "redirect:/api/v1/products";
    }

    @PutMapping
//    public String updateProduct(@ModelAttribute Product product) {
    public String updateProduct(@RequestParam Long id,
                                @RequestParam String title,
                                @RequestParam int price) {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);
        productService.saveOrUpdate(product);
        return "redirect:/api/v1/products";
    }
}
