package ru.geekbrains.hibernate;

import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
public final class EntityManagerFactoryComponent {
    private EntityManagerFactory factory;

    @PostConstruct
    public void init() {
        factory = new Configuration()
                .configure("hibernate.config.xml")
                .buildSessionFactory();
        EntityManager entityManager = null;
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

    public EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
