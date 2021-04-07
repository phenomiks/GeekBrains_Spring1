package ru.geekbrains.springsecurity.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springsecurity.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
