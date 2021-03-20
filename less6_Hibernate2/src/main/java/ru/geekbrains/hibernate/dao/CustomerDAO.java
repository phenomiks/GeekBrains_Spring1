package ru.geekbrains.hibernate.dao;

import org.springframework.stereotype.Component;
import ru.geekbrains.hibernate.SingletonEntityManagerFactory;
import ru.geekbrains.hibernate.entities.Customer;
import ru.geekbrains.hibernate.entities.Product;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerDAO {
    private EntityManager em;
    private SingletonEntityManagerFactory singletonEntityManagerFactory;

    @PostConstruct
    public void init() {
        singletonEntityManagerFactory = SingletonEntityManagerFactory.getInstance();
    }

    public Optional<Customer> findById(Long id) {
        em = singletonEntityManagerFactory.getFactory().createEntityManager();
        Optional<Customer> customer = Optional.empty();
        try {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.id = :id").setParameter("id", id);
            customer = Optional.ofNullable((Customer) query.getSingleResult());
        } catch (NoResultException e) {
            System.out.println("Customer not found");
        } finally {
            em.close();
        }
        return customer;
    }

    public List<Product> findCustomerProducts(Long id) {
        em = singletonEntityManagerFactory.getFactory().createEntityManager();
        em.getTransaction().begin();
        List<Product> productList = em.find(Customer.class, id).getProducts();
        em.getTransaction().commit();
        em.close();
        return productList;
    }

    public List<Customer> findAll() {
        em = singletonEntityManagerFactory.getFactory().createEntityManager();
        Query query = em.createQuery("SELECT c FROM Customer c");
        List<Customer> customerList = (List<Customer>) query.getResultList();
        em.close();
        return customerList;
    }

    public void saveOrUpdate(Customer customer) {
        Optional<Customer> optional = findById(customer.getId());
        em = singletonEntityManagerFactory.getFactory().createEntityManager();
        em.getTransaction().begin();
        if (optional.isPresent()) {
            Customer foundCustomer = optional.get();
            foundCustomer.setName(customer.getName());
            em.merge(foundCustomer);
        } else {
            em.persist(customer);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void deleteById(Long id) {
        em = singletonEntityManagerFactory.getFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            Customer customer = em.find(Customer.class, id);
            em.remove(customer);
            em.getTransaction().commit();
            System.out.println("DELETE: " + customer.toString());
        } catch (IllegalArgumentException e) {
            System.out.println("Not deleted. There is no customer with this ID");
        } finally {
            em.close();
        }
    }
}
