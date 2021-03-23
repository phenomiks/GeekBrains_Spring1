package ru.geekbrains.springdata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.springdata.entities.Product;
import ru.geekbrains.springdata.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    public Product deleteById(Long id) {
        Product product = getById(id);
        productRepository.deleteById(id);
        return product;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getProductsWithPriceGreaterValue(int min) {
        return productRepository.findByPriceGreaterThanEqual(min);
    }

    public List<Product> getProductsWithPriceLessValue(int max) {
        return productRepository.findByPriceLessThanEqual(max);
    }

    public List<Product> getProductsWithPriceBetween(int min, int max) {
        return productRepository.findByPriceBetween(min, max);
    }
}
