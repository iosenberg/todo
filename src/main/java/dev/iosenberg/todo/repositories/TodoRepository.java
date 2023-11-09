package dev.iosenberg.todo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.iosenberg.todo.models.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo,Long>, PagingAndSortingRepository<Todo, Long>{

}
