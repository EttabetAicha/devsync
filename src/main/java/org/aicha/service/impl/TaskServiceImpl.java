package org.aicha.service.impl;

import org.aicha.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskServiceImpl {
    public Optional<Task> save(Task task);
    List<Task> findAll();
    Optional<Task> findById(Long id);
    Optional<Task> update(Task task);
    Optional<Task> delete(Task task);
}
