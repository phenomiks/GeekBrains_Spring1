package ru.geekbrains.market.entities;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class Cart {
    private HashMap<Product, Integer> products;
    private Integer totalCost;

    @PostConstruct
    public void init() {
        products = new LinkedHashMap<>();
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }
}
