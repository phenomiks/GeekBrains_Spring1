package ru.geekbrains.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.market.entities.Cart;
import ru.geekbrains.market.entities.Product;
import ru.geekbrains.market.exceptions.ProductNotFoundException;

import java.util.List;

@Service
public class CartService {
    private final ProductService productService;
    private final Cart cart;

    @Autowired
    public CartService(ProductService productService, Cart cart) {
        this.productService = productService;
        this.cart = cart;
    }

    public List<Product> findAllProductInTheCart() {
        return cart.getProducts();
    }

    public void addProductByIdToCart(Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException
                ("No product found with id = " + id));
        cart.getProducts().add(product);
    }

    public void removeProductByIdInTheCart(Long id) {
        cart.getProducts().removeIf(p -> p.getId().equals(id));
    }
}
