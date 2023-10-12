package dev.iosenberg.todo.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.iosenberg.todo.model.User;

public interface UserRepository extends CrudRepository<User,Long> {
    
}
