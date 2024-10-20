package org.aicha.model;

import jakarta.persistence.*;
import org.aicha.model.enums.RequestType;
import org.aicha.model.enums.UserRole;

import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = {
            CascadeType.REMOVE,
            CascadeType.MERGE
    })
    private Token token;

    @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY)
    private Set<Request> requests = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String username, String email, String password, String firstName, String lastName, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @OneToMany(mappedBy = "assignedTo")
    private Set<Task> assignedTasks = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    private Set<Task> createdTasks = new HashSet<>();

    public Set<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public Set<Task> getCreatedTasks() {
        return createdTasks;
    }

    public void addAssignedTask(Task task) {
        assignedTasks.add(task);
    }

    public void addCreatedTask(Task task) {
        createdTasks.add(task);
    }

    public void removeAssignedTask(Task task) {
        assignedTasks.remove(task);
    }

    public void removeCreatedTask(Task task) {
        createdTasks.remove(task);
    }

    public List<Request> getRequests() {
        return new ArrayList<>(requests);
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    public void removeRequest(Request request) {
        requests.remove(request);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id);
    }

    public Boolean canRequest(RequestType type) {
        switch (type) {
            case MODIFICATION:
                return token != null && token.getModificationTokens() > 0;
            case DELETION:
                return token != null && token.getDeletionTokens() > 0;
            default:
                return false;
        }
    }

    public User useToken(RequestType type) {
        switch (type) {
            case MODIFICATION:
                token.setModificationTokens(token.getModificationTokens() - 1);
                break;
            case DELETION:
                token.setDeletionTokens(token.getDeletionTokens() - 1);
                break;
        }
        return this;
    }
}
