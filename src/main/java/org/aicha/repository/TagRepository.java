package org.aicha.repository;

import jakarta.persistence.EntityManager;
import org.aicha.interfaces.RepositoryInterface;
import org.aicha.model.Tag;
import org.aicha.util.EntityManagerProvider;

import java.util.List;
import java.util.Optional;

public class TagRepository implements RepositoryInterface<Tag> {

    private EntityManager getEntityManager() {
        return EntityManagerProvider.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Tag tag = entityManager.find(Tag.class, id);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(tag);
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Tag> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Tag> tags = entityManager.createQuery("SELECT t FROM Tag t LEFT JOIN FETCH t.tasks", Tag.class).getResultList();
            entityManager.getTransaction().commit();
            return tags;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Tag> create(Tag tag) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tag);
            entityManager.getTransaction().commit();
            return Optional.of(tag);
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Tag> update(Tag tag) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Tag updatedTag = entityManager.merge(tag);
            entityManager.getTransaction().commit();
            return Optional.of(updatedTag);
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Tag delete(Tag tag) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(tag) ? tag : entityManager.merge(tag));
            entityManager.getTransaction().commit();
            return tag;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public Optional<Tag> findByName(String name) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Tag tag = entityManager.createQuery("SELECT u FROM Tag u WHERE u.name = :name", Tag.class)
                    .setParameter("name", name)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return Optional.ofNullable(tag);
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }
}
