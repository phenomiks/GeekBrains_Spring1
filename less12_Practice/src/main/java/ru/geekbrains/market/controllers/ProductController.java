package ru.geekbrains.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.market.entities.Product;
import ru.geekbrains.market.exceptions.ProductNotFoundException;
import ru.geekbrains.market.services.ProductService;

@Controller
@RequestMapping(value = "/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("allProducts", productService.findAll());
        return "index";
    }

    @GetMapping(value = "/{id}")
    public String getProductById(Model model, @PathVariable Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException
                ("No product found with id = " + id));
        model.addAttribute("allProducts", product);
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
    public String updateProduct(@ModelAttribute Product product) {
        productService.saveOrUpdate(product);
        return "redirect:/api/v1/products";
    }
}
