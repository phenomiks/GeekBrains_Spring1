package ru.geekbrains.thymeleaf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.thymeleaf.entities.Product;
import ru.geekbrains.thymeleaf.services.ProductService;

@Controller
@RequestMapping(value = "products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("allProducts", productService.findAll());
        return "index";
    }

    @PostMapping
    public String saveProduct(@RequestParam String title,
                              @RequestParam int price) {
        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping(value = "/{id}")
    public String getProductById(@PathVariable Long id,
                                 Model model) {
        model.addAttribute("allProducts", productService.findProductById(id).get());
        return "index";
    }

    @GetMapping(value = "/remove/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}
