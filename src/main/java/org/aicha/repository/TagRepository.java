package org.aicha.repository;

import jakarta.persistence.*;
import org.aicha.model.Tag;
import org.aicha.repository.impl.TagRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class TagRepository implements TagRepositoryImpl {
    private final EntityManager entityManager;

    public TagRepository() {
        this.entityManager = Persistence.createEntityManagerFactory("myPU").createEntityManager();
    }

    public void save(Tag tag) {
        executeInTransaction(entityManager -> entityManager.persist(tag));
    }

    public List<Tag> findAll() {
        return entityManager.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
    }

    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    private void executeInTransaction(EntityManagerConsumer action) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            action.accept(entityManager);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Re-throw the exception for higher-level handling
        }
    }

    public void close() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @FunctionalInterface
    private interface EntityManagerConsumer {
        void accept(EntityManager entityManager);
    }
}
