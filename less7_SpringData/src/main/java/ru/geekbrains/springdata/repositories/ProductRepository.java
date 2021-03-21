package ru.geekbrains.springdata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springdata.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceGreaterThanEqual(int min);
    List<Product> findByPriceLessThanEqual(int max);
    List<Product> findByPriceBetween(int min, int max);
}
