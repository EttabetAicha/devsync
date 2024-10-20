package org.aicha.service;

import org.aicha.model.Task;
import org.aicha.model.enums.TaskStatus;
import org.aicha.model.User;
import org.aicha.repository.TaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private  TaskRepository taskRepository;

    public TaskService() {
        taskRepository = new TaskRepository();
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
        return taskRepository.findById(id);
    }

    public Optional<Task> createTask(Task task) {

        if (task == null || task.getTitle() == null || task.getTitle().isEmpty()
            || task.getCreatedBy() == null || task.getAssignedTo() == null
            || task.getStartDate() == null || task.getDeadline() == null
            || task.getTags() == null || task.getTags().isEmpty()
            || task.getStatus() == null) {
            throw new IllegalArgumentException("Invalid task");
        }
        return taskRepository.create(task);
    }

    public Optional<Task> updateTask(Task task) {

        if (task == null || task.getTitle() == null || task.getTitle().isEmpty()
            || task.getCreatedBy() == null || task.getAssignedTo() == null
            || task.getStartDate() == null || task.getDeadline() == null
            || task.getTags() == null || task.getTags().isEmpty()
            || task.getStatus() == null || task.getId() == null || task.getId() <= 0) {
            throw new IllegalArgumentException("Invalid task");
        }
        return taskRepository.update(task);
    }

    public Boolean markTaskAsDone(Task task) {

        if (task == null || task.getId() == null || task.getId() <= 0) {
            throw new IllegalArgumentException("Invalid task");
        }

        if (!LocalDate.now().isAfter(task.getDeadline())) {
            task.setStatus(TaskStatus.done);
        }
        if(taskRepository.update(task).isPresent()){
            return taskRepository.update(task).get().getStatus().equals(TaskStatus.done);
        }
        return false;
    }

    public List<Task> getUserTasks(User user) {
        return taskRepository.findByAssignedTo(user);
    }

    public Task deleteTask(Task task) {
        if (task == null || task.getId() == null || task.getId() <= 0) {
            throw new IllegalArgumentException("Invalid task");
        }
        return taskRepository.delete(task);
    }

    public String validateTask(Task newTask) {
        if (newTask == null) {
            return "Not a task";
        }

        if(newTask.getCreatedBy() == null || newTask.getAssignedTo() == null){
            return "Task must have a creator and an assignee.";
        }

        if (newTask.getStartDate().isBefore(LocalDate.now())) {
            return "Task start date cannot be in the past.";
        }
        if (newTask.getTags().size() < 2) {
            return "Task must have at least 2 tags.";
        }
        if (!newTask.getStartDate().isAfter(LocalDate.now().plusDays(3))
            && !newTask.getAssignedTo().equals(newTask.getCreatedBy())) {
            return "Task should start at least after 3 days.";
        }
        if (newTask.getStartDate().isAfter(newTask.getDeadline())) {
            return "Task deadline should be after the start date.";
        }
        return null;
    }
    public List<Task> getTasksByUserAndTag(User user, String tag, LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.getTasksByUserAndTag(user, tag, startDate, endDate);
    }

    public int getTokensUsedByUser(User user) {
        return taskRepository.countTokensUsedByUser(user);
    }

    public double getCompletionPercentage(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return 0.0;
        }
        return taskRepository.calculateCompletionPercentage(tasks);
    }

}
