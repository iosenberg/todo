package dev.iosenberg.todo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private long userId;

    private String name;

    private String description;

    private boolean completed = false;

    public Todo() {}

    public Todo(long id, long userId, String name, String description, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.completed = completed;
    }

    public Todo(Object id, long userId, String name, String description, boolean completed) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.completed = completed;
    }

    public Todo(Object id, Object userId, String name, String description, boolean completed) {
        this.name = name;
        this.description = description;
        this.completed = completed;
    }

    public long getId() {
        return this.id;
    }

    public Todo setId(long id) {
        this.id = id;
        return this;
    }

    public long getUserId() {
        return this.userId;
    }

    public Todo setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Todo setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Todo setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public Todo setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }
}
