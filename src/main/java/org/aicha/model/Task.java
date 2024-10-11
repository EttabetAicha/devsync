package org.aicha.model;

import jakarta.persistence.*;
import org.aicha.model.enums.TaskStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate endDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "task_tag",
            joinColumns = @JoinColumn(name = "tasks_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id") // Match with the Tag entity
    )
    private List<Tag> tags; // Relationship with Tag

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false) // Ensure the status is not nullable
    private TaskStatus status; // Using the TaskStatus enum

    // Updated field names
    private boolean changed; // Indicates if the task has changed
    private boolean deleted; // Indicates if the task is deleted

    @ManyToOne
    @JoinColumn(name = "user_id") // User who created the task
    private User user;

    @ManyToOne
    @JoinColumn(name = "assignee_to") // User assigned to the task
    private User assigneeTo;

    // Default constructor
    public Task() {
    }

    // Constructor with parameters
    public Task(String title, String description, LocalDate creationDate, LocalDate endDate,
                TaskStatus status, boolean changed, User user, User assigneeTo, List<Tag> tags, boolean deleted) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.endDate = endDate;
        this.status = status;
        this.changed = changed; // Updated to match the field name
        this.user = user;
        this.assigneeTo = assigneeTo;
        this.tags = tags;
        this.deleted = deleted;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public boolean isChanged() { // Getter for changed
        return changed;
    }

    public void setChanged(boolean changed) { // Setter for changed
        this.changed = changed;
    }

    public boolean isDeleted() { // Getter for deleted
        return deleted;
    }

    public void setDeleted(boolean deleted) { // Setter for deleted
        this.deleted = deleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAssigneeTo() {
        return assigneeTo;
    }

    public void setAssigneeTo(User assigneeTo) {
        this.assigneeTo = assigneeTo;
    }
}
