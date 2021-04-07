package ru.geekbrains.springsecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springsecurity.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
