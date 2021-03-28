package ru.geekbrains.springrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springrest.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
