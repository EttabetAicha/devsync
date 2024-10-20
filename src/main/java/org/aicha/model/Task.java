package org.aicha.model;

import jakarta.persistence.*;
import org.aicha.model.enums.TaskStatus;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "is_token_modifiable", nullable = false)
    private Boolean isTokenModifiable;

    // Assigned to (User)
    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    // Created by (User)
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;


    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = {
                    @UniqueConstraint(
                            columnNames = {"task_id", "tag_id"}
                    )
            }
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private Set<Request> requests = new HashSet<>();

    public Task() {
    }

    public Task(String title, String description, TaskStatus status, LocalDate startDate,LocalDate deadline, Boolean isTokenModifiable, User assignedTo, User createdBy) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.deadline = deadline;
        this.isTokenModifiable = isTokenModifiable;
        this.assignedTo = assignedTo;
        this.createdBy = createdBy;
        this.createdAt = LocalDate.now();
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Boolean getTokenModifiable() {
        return isTokenModifiable;
    }

    public void setTokenModifiable(Boolean tokenModifiable) {
        isTokenModifiable = tokenModifiable;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<Tag> getTags() {
        return new ArrayList<>(tags);
    }

    public void addTag(Tag tag) {
        if (tags.add(tag))
            tag.getTasks().add(this);
    }

    public void removeTag(Tag tag) {
        if (tags.remove(tag))
            tag.getTasks().remove(this);
    }

    public void setTags(List<Tag> tags) {
        this.tags = new HashSet<>(tags);
    }

    public List<Request> getRequests() {
        return new ArrayList<>(requests);
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && status == task.status && Objects.equals(startDate, task.startDate) && Objects.equals(deadline, task.deadline) && Objects.equals(isTokenModifiable, task.isTokenModifiable) && Objects.equals(assignedTo, task.assignedTo) && Objects.equals(createdBy, task.createdBy) && Objects.equals(createdAt, task.createdAt) && Objects.equals(tags, task.tags) && Objects.equals(requests, task.requests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, startDate, deadline, isTokenModifiable, assignedTo, createdBy, createdAt, tags, requests);
    }
}
