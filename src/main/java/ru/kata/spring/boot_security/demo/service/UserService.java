package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User save(User user, User existingUser);

    User getById(Long id);

    User findByFirstname(String firstname);

    void delete(Long id);

    User update(Long id, User user) throws EntityNotFoundException;

}