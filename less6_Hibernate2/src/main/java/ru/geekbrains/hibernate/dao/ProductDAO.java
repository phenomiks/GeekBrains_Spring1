package ru.geekbrains.hibernate.dao;

import org.springframework.stereotype.Component;
import ru.geekbrains.hibernate.SingletonEntityManagerFactory;
import ru.geekbrains.hibernate.entities.Product;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDAO {
    private EntityManager em;
    private SingletonEntityManagerFactory singletonEntityManagerFactory;

    @PostConstruct
    public void init() {
        singletonEntityManagerFactory = SingletonEntityManagerFactory.getInstance();
    }

    public Optional<Product> findById(Long id) {
        em = singletonEntityManagerFactory.getFactory().createEntityManager();
        Optional<Product> product = Optional.empty();
        try {
            Query query = em.createQuery("SELECT p FROM Product p WHERE p.id = :id").setParameter("id", id);
            product = Optional.ofNullable((Product) query.getSingleResult());
        } catch (NoResultException e) {
            System.out.println("Product not found");
        } finally {
            em.close();
        }
        return product;
    }

    public List<Product> findAll() {
        em = singletonEntityManagerFactory.getFactory().createEntityManager();
        Query query = em.createQuery("SELECT p FROM Product p");
        List<Product> productList = (List<Product>) query.getResultList();
        em.close();
        return productList;
    }

    public void saveOrUpdate(Product product) {
        Optional<Product> optional = findById(product.getId());
        em = singletonEntityManagerFactory.getFactory().createEntityManager();
        em.getTransaction().begin();
        if (optional.isPresent()) {
            Product foundProduct = optional.get();
            foundProduct.setTitle(product.getTitle());
            foundProduct.setPrice(product.getPrice());
            em.merge(foundProduct);
        } else {
            em.persist(product);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void deleteById(Long id) {
        em = singletonEntityManagerFactory.getFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            Product product = em.find(Product.class, id);
            em.remove(product);
            em.getTransaction().commit();
            System.out.println("DELETE: " + product.toString());
        } catch (IllegalArgumentException e) {
            System.out.println("Not deleted. There is no product with this ID");
        } finally {
            em.close();
        }
    }
}
