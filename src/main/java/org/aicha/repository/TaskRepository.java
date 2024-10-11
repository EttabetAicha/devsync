package org.aicha.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.aicha.model.Task;
import org.aicha.repository.impl.TaskRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class TaskRepository implements TaskRepositoryImpl {
    private final EntityManagerFactory entityManagerFactory;

    public TaskRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("myPU");
    }

    @Override
    public Optional<Task> save(Task task) {
        return executeInTransaction(entityManager -> {
            updateTaskTags(entityManager, task);
            entityManager.persist(task);
        }, task);
    }

    @Override
    public List<Task> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(Task.class, id));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Task> update(Task task) {
        return executeInTransaction(entityManager -> {
            updateTaskTags(entityManager, task);
            entityManager.merge(task);
        }, task);
    }

    @Override
    public boolean delete(Task task) {
        return executeInTransaction(entityManager -> {
            Task managedTask = entityManager.contains(task) ? task : entityManager.merge(task);
            if (managedTask != null) {
                entityManager.remove(managedTask);
            }
        });
    }

    private void updateTaskTags(EntityManager entityManager, Task task) {
        if (task.getTags() != null) {
            task.getTags().forEach(tag -> {
                if (tag.getId() != null) {
                    entityManager.merge(tag);
                } else {
                    entityManager.persist(tag);
                }
            });
        }
    }

    private Optional<Task> executeInTransaction(EntityManagerConsumer action, Task task) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            action.accept(entityManager);
            transaction.commit();
            return Optional.of(task);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    private boolean executeInTransaction(EntityManagerConsumer action) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            action.accept(entityManager);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    @FunctionalInterface
    private interface EntityManagerConsumer {
        void accept(EntityManager entityManager);
    }

    public void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
