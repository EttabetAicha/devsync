package org.aicha.repository.impl;

import org.aicha.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryImpl {
    boolean save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Optional<User> deleteById(Long id);

    Optional<User> update(User user);

    List<User> getAll();
}
