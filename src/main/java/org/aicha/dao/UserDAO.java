package org.aicha.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aicha.model.User;


import java.util.List;

@Stateless
public class UserDAO {

    @PersistenceContext(unitName = "myPU")
    private EntityManager entityManager;

    public void create(User user) {
        entityManager.persist(user);
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public void update(User user) {
        entityManager.merge(user);
    }

    public void delete(User user) {
        if (!entityManager.contains(user)) {
            user = entityManager.merge(user);
        }
        entityManager.remove(user);
    }

    public User findByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}