package org.aicha.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.aicha.model.User;
import org.aicha.repository.impl.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class UserRepository implements UserRepositoryImpl {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public UserRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("myPU");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public boolean save(User user) {
        return executeInTransaction(entityManager -> {
            entityManager.persist(user);
            return true;
        });
    }

    public Optional<User> findByEmail(String email) {
        return findSingleResult("SELECT u FROM User u WHERE u.email = :email", "email", email);
    }

    public Optional<User> findByUsername(String username) {
        return findSingleResult("SELECT u FROM User u WHERE u.username = :username", "username", username);
    }

    public Optional<User> findById(Long id) {
        return findSingleResult("SELECT u FROM User u WHERE u.id = :id", "id", id);
    }

    public Optional<User> deleteById(Long id) {
        return executeInTransaction(entityManager -> {
            User user = entityManager.find(User.class, id);
            if (user != null) {
                entityManager.remove(user);
                return Optional.of(user);
            }
            return Optional.empty(); // Return Optional.empty if the user does not exist
        });
    }

    public Optional<User> update(User user) {
        return executeInTransaction(entityManager -> {
            return Optional.of(entityManager.merge(user));
        });
    }

    public List<User> getAll() {
        return executeInTransaction(entityManager -> {
            String query = "SELECT u FROM User u";
            return entityManager.createQuery(query, User.class).getResultList();
        });
    }

    private Optional<User> findSingleResult(String queryString, String paramName, Object paramValue) {
        return executeInTransaction(entityManager -> {
            List<User> users = entityManager.createQuery(queryString, User.class)
                    .setParameter(paramName, paramValue)
                    .getResultList();
            return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
        });
    }

    private <T> T executeInTransaction(EntityManagerFunction<T> action) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            T result = action.apply(entityManager);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Rethrow the exception after rollback
        }
    }

    @FunctionalInterface
    private interface EntityManagerFunction<T> {
        T apply(EntityManager entityManager);
    }

    public void close() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
