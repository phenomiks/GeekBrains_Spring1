package ru.geekbrains.springrest2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.springrest2.entities.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final ProductService productService;
    private List<Product> products;

    @Autowired
    public CartService(ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        products = new ArrayList<>();
    }

    public List<Product> findAllProductInTheCart() {
        return products;
    }

    public void addProductByIdToCart(Long id) {
        Optional<Product> product = productService.findProductById(id);
        product.ifPresent(p -> products.add(p));
    }

    public void removeProductByIdInTheCart(Long id) {
        int searchId = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                searchId = i;
                break;
            }
        }
        if (searchId != -1) {
            products.remove(searchId);
        }
    }
}
