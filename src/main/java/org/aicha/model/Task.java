package org.aicha.model;

import jakarta.persistence.*;
import org.aicha.model.enums.TaskStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    private Long userId;

    public Task() {
    }

    public Task(String title, String description, LocalDateTime dueDate, Long userId) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.createdDate = LocalDateTime.now();
        this.dueDate = dueDate;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(dueDate);
    }

    public void markAsCompleted() {
        this.status = TaskStatus.COMPLETED;
    }

    public void markAsCancelled() {
        this.status = TaskStatus.CANCELLED;
    }
}
