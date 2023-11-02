package dev.iosenberg.todo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import dev.iosenberg.todo.model.Todo;

public interface TodoRepository extends CrudRepository<Todo,Long>, PagingAndSortingRepository<Todo, Long>{

}
