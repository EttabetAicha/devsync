package org.aicha.service;

import org.aicha.model.User;
import org.aicha.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        // validate id
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }

        return userRepository.findById(id);
    }

    public Optional<User> createUser(User user) {
        // validate user
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Invalid user");
        }

        if (this.getUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        return userRepository.create(user);
    }

    public Optional<User> updateUser(User user) {

        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getId() == null || user.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user");
        }
        return userRepository.update(user);
    }

    public Optional<User> getUserByEmail(String email) {

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        return userRepository.findByEmail(email);
    }

    public User deleteUser(User u) {
        if (u == null || u.getId() == null || u.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user");
        }

        return userRepository.delete(u);
    }
}
