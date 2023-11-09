package dev.iosenberg.todo.models;

import org.springframework.data.annotation.Id;

public record User(@Id Long id, String username, String password) {
}
