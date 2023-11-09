package dev.iosenberg.todo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.iosenberg.todo.models.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    // User findUsernameAndPassword(String username, String password);

    // User findTopByUsername(String username);

    // User findTopByPassword(String password);
}
