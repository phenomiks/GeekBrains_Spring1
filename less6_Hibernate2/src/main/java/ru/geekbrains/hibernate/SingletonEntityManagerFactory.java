package ru.geekbrains.hibernate;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public final class SingletonEntityManagerFactory {
    private static SingletonEntityManagerFactory instance;
    private final EntityManagerFactory factory;
    private EntityManager entityManager;

    private SingletonEntityManagerFactory() {
        factory = new Configuration()
                .configure("hibernate.config.xml")
                .buildSessionFactory();
        try {
            Path pathToFile = Paths.get(".\\src\\main\\resources\\SQLCommands.sql");
            String sql = Files.lines(pathToFile).collect(Collectors.joining(" "));
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(sql).executeUpdate();
            entityManager.getTransaction().commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static SingletonEntityManagerFactory getInstance() {
        if (instance == null) {
            instance = new SingletonEntityManagerFactory();
        }
        return instance;
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityManagerFactory getFactory() {
        return factory;
    }
}
