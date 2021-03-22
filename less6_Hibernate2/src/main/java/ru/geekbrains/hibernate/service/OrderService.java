package ru.geekbrains.hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.geekbrains.hibernate.dao.CustomerDAO;
import ru.geekbrains.hibernate.dao.ProductDAO;
import ru.geekbrains.hibernate.entities.Customer;
import ru.geekbrains.hibernate.entities.Product;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements CommandLineRunner {
    private CustomerDAO customerDAO;
    private ProductDAO productDAO;

    @Autowired
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void findCustomer(Long id) {
        Optional<Customer> customer = customerDAO.findById(id);
        customer.ifPresent(System.out::println);
        System.out.println("Products:");
        findCustomerProducts(id);
    }

    public void findCustomerProducts(Long id) {
        List<Product> productList = customerDAO.findCustomerProducts(id);
        productList.forEach(p -> System.out.println(p.getTitle() + " " + p.getPrice()));
    }

    public void findProducts(Long id) {
        Optional<Product> product = productDAO.findById(id);
        if (product.isPresent()) {
            System.out.println(product.get());
            System.out.println("Customers:");
            findProductCustomers(id);
        }
    }

    public void findProductCustomers(Long id) {
        List<Customer> customerList = productDAO.findProductCustomers(id);
        customerList.forEach(c -> System.out.println(c.getName()));
    }

    @Override
    public void run(String... args) {
        findCustomer(1L);
        findProducts(3L);
    }
}
