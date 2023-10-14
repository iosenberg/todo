package dev.iosenberg.todo.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.iosenberg.todo.model.Todo;
import dev.iosenberg.todo.repositories.TodoRepository;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private TodoRepository todoRepository;
    
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Todo> findById(@PathVariable Long requestedId) {
        Optional<Todo> todoOptional = todoRepository.findById(requestedId);
        if(todoOptional.isPresent()) {
            return ResponseEntity.ok(todoOptional.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createTodo(@RequestBody Todo newTodoRequest, UriComponentsBuilder ucb) {
        Todo savedTodo = todoRepository.save(newTodoRequest);
        URI locationOfNewTodo = ucb
            .path("todos/{id}")
            .buildAndExpand(savedTodo.id())
            .toUri();
        return ResponseEntity.created(locationOfNewTodo).build();
    }
}