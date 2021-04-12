package ru.geekbrains.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.market.entities.Cart;
import ru.geekbrains.market.entities.Product;
import ru.geekbrains.market.exceptions.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
    private final ProductService productService;
    private final Cart cart;

    @Autowired
    public CartService(ProductService productService, Cart cart) {
        this.productService = productService;
        this.cart = cart;
    }

    public HashMap<Product, Integer> findAllProductInTheCart() {
        return cart.getProducts();
    }

    public void addProductByIdToCart(Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException
                ("No product found with id = " + id));
        cart.getProducts().merge(product, 1, Integer::sum);
        recalculateTotalCost();
    }

    public void removeProductByIdInTheCart(Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException
                ("No product found with id = " + id));
        cart.getProducts().remove(product);
        recalculateTotalCost();
    }

    public void removeOneProductByIdInTheCart(Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException
                ("No product found with id = " + id));
        if (cart.getProducts().get(product).equals(1)) {
            cart.getProducts().remove(product);
        } else {
            cart.getProducts().merge(product, -1, Integer::sum);
        }
        recalculateTotalCost();
    }

    public void recalculateTotalCost() {
        int totalCost = 0;
        for (Map.Entry<Product, Integer> c : cart.getProducts().entrySet()) {
            totalCost += c.getKey().getPrice() * c.getValue();
        }
        cart.setTotalCost(totalCost);
    }

    public Integer getTotalCost() {
        return cart.getTotalCost();
    }

    public void clearCart() {
        cart.getProducts().clear();
    }
}
