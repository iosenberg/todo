package dev.iosenberg.todo.model;

import org.springframework.data.annotation.Id;

public record Todo(@Id Long id, String name, String description, boolean completed) {

}
