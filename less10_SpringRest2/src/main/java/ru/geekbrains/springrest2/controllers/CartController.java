package ru.geekbrains.springrest2.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springrest2.entities.Product;
import ru.geekbrains.springrest2.services.CartService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @ApiOperation("Getting a list of products in the cart")
    public List<Product> getAllProductsInTheCart() {
        return cartService.findAllProductInTheCart();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("Removing a product from the cart by its id")
    public void deleteProductByIdInTheCart(@PathVariable Long id) {
        cartService.removeProductByIdInTheCart(id);
    }

    @PostMapping(value = "/{id}")
    @ApiOperation("Saving a product to the cart by its id")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addProductByIdToCart(id);
    }
}
