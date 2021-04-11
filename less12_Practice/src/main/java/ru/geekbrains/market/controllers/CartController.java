package ru.geekbrains.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.market.services.CartService;


@Controller
@RequestMapping(value = "/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String getAllProductsInTheCart(Model model) {
        model.addAttribute("allProductsInTheCart", cartService.findAllProductInTheCart());
        model.addAttribute("totalCost", cartService.getTotalCost());
        return "cart";
    }

    @PostMapping(value = "/{id}")
    public String addProductToCart(@PathVariable Long id) {
        cartService.addProductByIdToCart(id);
        return "redirect:/api/v1/products";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteProductByIdInTheCart(@PathVariable Long id) {
        cartService.removeProductByIdInTheCart(id);
        return "redirect:/api/v1/cart";
    }

    @DeleteMapping
    public String clearCart() {
        cartService.clearCart();
        return "cart";
    }
}
