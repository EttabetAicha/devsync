package org.aicha.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.aicha.model.User;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class AuthService {

    @PersistenceContext(unitName = "myPU")
    private EntityManager entityManager;

    private static final String USERNAME_PARAM = "username";

    public String registerUser(User user) {

        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return "Invalid user input.";
        }
        if (getUserByUsername(user.getUsername()) != null) {
            return "Username already exists.";
        }

        user.setPassword(hashPassword(user.getPassword()));
        entityManager.persist(user);

        return "User registered successfully.";
    }

    public String authenticate(String username, String password) {
        if (username == null || password == null) {
            return "Username and password must not be null.";
        }

        try {

            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter(USERNAME_PARAM, username);
            User user = query.getSingleResult();
            System.out.println("Retrieved user: " + user.getUsername());


            if (BCrypt.checkpw(password, user.getPassword())) {
                return "Authentication successful for user: " + username;
            } else {
                System.out.println("Password does not match for user: " + username);
                return "Invalid username or password.";
            }
        } catch (NoResultException e) {
            System.out.println("No user found with username: " + username);
            return "Invalid username or password.";
        } catch (Exception e) {
            System.out.println("Authentication error: " + e.getMessage());
            return "An error occurred during authentication.";
        }
    }

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    private User getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :" + USERNAME_PARAM, User.class);
        query.setParameter(USERNAME_PARAM, username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Error retrieving user: " + e.getMessage());
            return null;
        }
    }
}
