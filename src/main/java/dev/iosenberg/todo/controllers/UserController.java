package dev.iosenberg.todo.controllers;

import java.net.URI;
import java.util.Optional;

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
            .buildAndExpand(savedUser.getId())
            .toUri();
        return ResponseEntity.created(locationOfNewUser).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Void> putUser(@PathVariable Long id, @RequestBody User userUpdate) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            User updatedUser = userUpdate;
            userRepository.save(updatedUser);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if(!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
