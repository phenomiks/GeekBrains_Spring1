package ru.geekbrains.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.market.services.CartService;

import javax.servlet.http.HttpServletRequest;


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
    public String addProductToCart(@PathVariable Long id, HttpServletRequest request) {
        cartService.addProductByIdToCart(id);
        return "redirect:" + request.getHeader("referer");
    }

    @DeleteMapping(value = "/{id}")
    public String deleteProductByIdInTheCart(@PathVariable Long id,
                                             @RequestParam(value = "delete1", required = false, defaultValue = "false") Boolean delete1) {
        if (delete1) {
            cartService.removeOneProductByIdInTheCart(id);
        } else {
            cartService.removeProductByIdInTheCart(id);
        }
        return "redirect:/api/v1/cart";
    }

    @DeleteMapping
    public String clearCart() {
        cartService.clearCart();
        return "cart";
    }
}
