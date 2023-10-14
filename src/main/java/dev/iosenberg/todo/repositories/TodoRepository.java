package dev.iosenberg.todo.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.iosenberg.todo.model.Todo;

public interface TodoRepository extends CrudRepository<Todo,Long> {

}
