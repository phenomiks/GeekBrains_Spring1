package ru.geekbrains.market.entities;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Cart {
    private List<Product> products;

    @PostConstruct
    public void init() {
        products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
