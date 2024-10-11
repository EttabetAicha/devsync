package org.aicha.repository.impl;

import org.aicha.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepositoryImpl {
    Optional<Task> save(Task task);
    List<Task> findAll();
    Optional<Task> findById(Long id);
    Optional<Task> update(Task task);
    boolean delete(Task task);
}
