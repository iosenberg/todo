package dev.iosenberg.todo.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.iosenberg.todo.models.Todo;
import dev.iosenberg.todo.repositories.TodoRepository;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;
    
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> findAll(Pageable pageable) {
        Page<Todo> page = todoRepository.findAll(
            PageRequest.of(1,1)
            );
        return ResponseEntity.ok(page.getContent());
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

    @PutMapping("/{id}")
    private ResponseEntity<Void> putTodo(@PathVariable Long id, @RequestBody Todo todoUpdate) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        if(todoOptional.isPresent()) {
            Todo updatedTodo = todoUpdate;
            todoRepository.save(updatedTodo);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if(!todoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}