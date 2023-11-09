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

import dev.iosenberg.todo.models.User;
import dev.iosenberg.todo.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
    
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<User> findById(@PathVariable Long requestedId) {
        Optional<User> userOptional = userRepository.findById(requestedId);
        if(userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User newUserRequest, UriComponentsBuilder ucb) {
        User savedUser = userRepository.save(newUserRequest);
        URI locationOfNewUser = ucb
            .path("users/{id}")
            .buildAndExpand(savedUser.id())
            .toUri();
        return ResponseEntity.created(locationOfNewUser).build();
    }
}
