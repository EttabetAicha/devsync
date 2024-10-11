package org.aicha.service;

import org.aicha.model.Task;
import org.aicha.repository.TaskRepository;
import org.aicha.repository.impl.TaskRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class TaskService implements TaskRepositoryImpl {
    private final TaskRepositoryImpl taskRepository;

    public TaskService() {
        this.taskRepository = new TaskRepository();
    }

    public Optional<Task> save(Task task) {
        return taskRepository.save(task);
    }
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }
    public Optional<Task> update(Task task) {
        return taskRepository.update(task);
    }
    public boolean delete(Task task) {
        return taskRepository.delete(task);
    }
}