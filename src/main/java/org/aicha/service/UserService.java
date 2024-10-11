package org.aicha.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aicha.model.User;
import org.aicha.repository.UserRepository;
import org.aicha.service.impl.UserServiceImpl;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserService implements UserServiceImpl {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public Optional<User> register(User user, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();

        if (user == null) {
            session.setAttribute("emptyUser", "User cannot be null");
            return Optional.empty();  // Return an empty Optional if user is null
        }

        // Check for existing email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            session.setAttribute("existEmail", "Email already exists");
            return Optional.empty();
        }

        // Check for existing username
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            session.setAttribute("existUsername", "Username already exists");
            return Optional.empty();
        }

        // Save the user if both checks are passed
        userRepository.save(user);
        return Optional.of(user);
    }

    public Optional<User> login(String username, String password) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            if (BCrypt.checkpw(password, user.get().getPassword())) {
                return user; // Login successful
            } else {
                throw new Exception("Wrong password");
            }
        }

        return Optional.empty(); // Username not found
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    // Updated to accept Long id instead of User
    public Optional<User> deleteById(Long id) {
        return userRepository.deleteById(id);
    }

    public Optional<User> update(User user) {
        return userRepository.update(user);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }
}
