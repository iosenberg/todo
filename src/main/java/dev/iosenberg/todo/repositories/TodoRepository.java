package dev.iosenberg.todo.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.iosenberg.todo.model.User;

public interface TodoRepository extends CrudRepository<User,Long> {
    
}
