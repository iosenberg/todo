package dev.iosenberg.todo.model;

import org.springframework.data.annotation.Id;

public record User(@Id Long id, String username, String password) {
}
