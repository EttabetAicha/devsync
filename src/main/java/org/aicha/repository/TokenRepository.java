package org.aicha.repository;

import jakarta.persistence.EntityManager;
import org.aicha.interfaces.RepositoryInterface;
import org.aicha.model.Token;
import org.aicha.util.EntityManagerProvider;

import java.util.List;
import java.util.Optional;

public class TokenRepository implements RepositoryInterface<Token> {

    private EntityManager getEntityManager() {
        return EntityManagerProvider.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public Optional<Token> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Token token = entityManager.find(Token.class, id);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(token);
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Token> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Token> tokens = entityManager.createQuery("SELECT u FROM Token u", Token.class).getResultList();
            entityManager.getTransaction().commit();
            return tokens;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Token> create(Token token) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(token);
            entityManager.getTransaction().commit();
            return Optional.of(token);
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Token> update(Token token) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Token updatedToken = entityManager.merge(token);
            entityManager.getTransaction().commit();
            return Optional.of(updatedToken);
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Token delete(Token token) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(token) ? token : entityManager.merge(token));
            entityManager.getTransaction().commit();
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }
}
