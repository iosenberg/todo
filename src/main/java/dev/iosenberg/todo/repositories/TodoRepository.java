package dev.iosenberg.todo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dev.iosenberg.todo.model.Todo;

public interface TodoRepository extends CrudRepository<Todo,Long> {
    Todo findById();
    Todo findByName();

    List<Todo> findAllByUserId();
}
