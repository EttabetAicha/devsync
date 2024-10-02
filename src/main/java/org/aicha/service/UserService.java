package org.aicha.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aicha.model.User;

import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "myPU")
    private EntityManager entityManager;

    public void createUser(User user) {
        entityManager.persist(user);
    }

    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
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
