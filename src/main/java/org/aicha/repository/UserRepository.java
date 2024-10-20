package org.aicha.repository;

import jakarta.persistence.EntityManager;
import org.aicha.interfaces.RepositoryInterface;
import org.aicha.model.User;
import org.aicha.util.EntityManagerProvider;
import org.aicha.util.StringUtil;

import java.util.List;
import java.util.Optional;

public class UserRepository implements RepositoryInterface<User> {

    private EntityManager getEntityManager() {
        return EntityManagerProvider.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public Optional<User> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, id);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<User> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
            entityManager.getTransaction().commit();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<User> create(User user) {
        EntityManager entityManager = getEntityManager();
        // Hash password
        user.setPassword(StringUtil.hashPassword(user.getPassword()));
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<User> update(User user) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            User existingUser = entityManager.find(User.class, user.getId());
            if (!existingUser.getPassword().equals(user.getPassword())) {
                user.setPassword(StringUtil.hashPassword(user.getPassword()));
            }
            User updatedUser = entityManager.merge(user);
            entityManager.getTransaction().commit();
            return Optional.of(updatedUser);
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public User delete(User user) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public Optional<User> findByEmail(String email) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    public Optional<User> findByUsername(String username) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }
}
