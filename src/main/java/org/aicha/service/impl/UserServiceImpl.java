package org.aicha.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.aicha.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceImpl {
    Optional<User> register(User user, HttpServletRequest request) throws Exception;

    Optional<User> login(String username, String password) throws Exception;

    Optional<User> getById(Long id);

    // Updated to accept Long id instead of User
    Optional<User> deleteById(Long id);

    Optional<User> update(User user);

    List<User> getAll();
}
