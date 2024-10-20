package org.aicha.model;

import jakarta.persistence.*;
import org.aicha.model.enums.RequestStatus;
import org.aicha.model.enums.RequestType;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.REMOVE
    })
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.REMOVE
    })
    @JoinColumn(name = "task_id", nullable = false, updatable = false)
    private Task task;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestType type;

    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'PENDING'")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(name = "requested_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime requestedAt;

    @Column(name = "responded_at", nullable = true)
    private LocalDateTime respondedAt;


    public Request() {
        this.status = RequestStatus.PENDING;
        this.requestedAt = LocalDateTime.now();
        this.respondedAt = null;
    }

    public Request(User requester, Task task, RequestType type) {
        this.requester = requester;
        this.task = task;
        this.type = type;
        this.status = RequestStatus.PENDING;
        this.requestedAt = LocalDateTime.now();
        this.respondedAt = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(LocalDateTime respondedAt) {
        this.respondedAt = respondedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) && Objects.equals(requester, request.requester) && Objects.equals(task, request.task) && type == request.type && status == request.status && Objects.equals(requestedAt, request.requestedAt) && Objects.equals(respondedAt, request.respondedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requester, task, type, status, requestedAt, respondedAt);
    }
}
