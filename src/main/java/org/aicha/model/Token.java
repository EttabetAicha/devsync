package org.aicha.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "modification_tokens")
    private Integer modificationTokens;

    @Column(name = "deletion_tokens")
    private Integer deletionTokens;

    @Column(name = "last_reset_date")
    private LocalDateTime lastResetDate;

    public Token() {
    }

    public Token(User user, Integer modificationTokens, Integer deletionTokens, LocalDateTime lastResetDate) {
        this.user = user;
        this.modificationTokens = modificationTokens;
        this.deletionTokens = deletionTokens;
        this.lastResetDate = lastResetDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getModificationTokens() {
        return modificationTokens;
    }

    public void setModificationTokens(Integer modificationTokens) {
        this.modificationTokens = modificationTokens;
    }

    public Integer getDeletionTokens() {
        return deletionTokens;
    }

    public void setDeletionTokens(Integer deletionTokens) {
        this.deletionTokens = deletionTokens;
    }

    public LocalDateTime getLastResetDate() {
        return lastResetDate;
    }

    public void setLastResetDate(LocalDateTime lastResetDate) {
        this.lastResetDate = lastResetDate;
    }

    public void duplicateModificationTokens() {
        this.modificationTokens *= 2;
    }
}
