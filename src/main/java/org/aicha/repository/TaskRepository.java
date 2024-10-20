package org.aicha.repository;

import jakarta.persistence.EntityManager;
import org.aicha.interfaces.RepositoryInterface;
import org.aicha.model.Tag;
import org.aicha.model.Task;
import org.aicha.model.User;
import org.aicha.model.enums.TaskStatus;
import org.aicha.util.EntityManagerProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TaskRepository implements RepositoryInterface<Task> {

    private EntityManager getEntityManager() {
        return EntityManagerProvider.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public Optional<Task> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Task task = entityManager.find(Task.class, id);
            if (task != null) {
                entityManager.createQuery("SELECT t FROM Task t LEFT JOIN FETCH t.tags WHERE t.id = :id", Task.class)
                        .setParameter("id", id)
                        .getSingleResult();
            }
            entityManager.getTransaction().commit();
            return Optional.ofNullable(task);
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
    public List<Task> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Task> tasks = entityManager.createQuery("SELECT t FROM Task t LEFT JOIN FETCH t.tags LEFT JOIN FETCH t.requests", Task.class
            ).getResultList();
            entityManager.getTransaction().commit();
            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public Optional<Task> create(Task task) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(task);
            entityManager.getTransaction().commit();
            return Optional.of(task);
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
    public Optional<Task> update(Task task) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Task updatedTask = entityManager.merge(task);
            entityManager.getTransaction().commit();
            return Optional.of(updatedTask);
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
    public Task delete(Task task) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(task) ? task : entityManager.merge(task));
            entityManager.getTransaction().commit();
            return task;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Task> findByAssignedTo(User user) {
        List<Task> tasks = new ArrayList<>();
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            tasks = entityManager.createQuery(
                            "SELECT t FROM Task t LEFT JOIN FETCH t.tags LEFT JOIN FETCH t.requests WHERE t.assignedTo = :user", Task.class)
                    .setParameter("user", user)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return tasks;
    }

    public List<Task> getTasksByUserAndTag(User user, String tag, LocalDateTime startDate, LocalDateTime endDate) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Task> tasks = entityManager.createQuery(
                            "SELECT t FROM Task t LEFT JOIN t.tags tg WHERE t.assignedTo = :user AND tg.name = :tag AND t.startDate >= :startDate AND t.deadline <= :endDate", Task.class)
                    .setParameter("user", user)
                    .setParameter("tag", tag)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
            System.out.println("Tasks: " + tasks);
            entityManager.getTransaction().commit();
            return tasks;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public int countTokensUsedByUser(User user) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Long count = entityManager.createQuery(
                            "SELECT SUM(ut.deletionTokens + ut.modificationTokens) FROM Token ut WHERE ut.user = :user", Long.class)
                    .setParameter("user", user)
                    .getSingleResult();
            System.out.println("Count: " + count);
            entityManager.getTransaction().commit();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public double calculateCompletionPercentage(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return 0.0;
        }
        long completedTasks = tasks.stream().filter(task -> task.getStatus() == TaskStatus.done).count();
        return (double) completedTasks / tasks.size() * 100;
    }
}
