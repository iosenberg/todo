package dev.iosenberg.todo.model;

import java.util.Set;

import org.springframework.data.annotation.Id;

public record User(@Id Long id, String username, String password) {
}
