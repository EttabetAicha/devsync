package org.aicha.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tags")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks;

    public Tag(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }
    public Tag() {}
}
