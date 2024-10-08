package org.aicha.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.aicha.model.User;

import jakarta.persistence.NoResultException;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "myPU")
    private EntityManager entityManager;

    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    public User getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // User not found
        }
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public void updateUser(User user) {
        entityManager.merge(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}
