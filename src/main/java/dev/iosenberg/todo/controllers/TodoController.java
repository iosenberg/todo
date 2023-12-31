package dev.iosenberg.todo.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import dev.iosenberg.todo.models.Todo;
import dev.iosenberg.todo.repositories.TodoRepository;

@Controller
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @GetMapping
    public String getAllTodos(Model model, @ModelAttribute Todo todo) {
        List<Todo> todos = todoRepository.findAll();

        model.addAttribute("todos", todos);
        model.addAttribute("todo", new Todo());
        return "test";
    }
    
    @PostMapping("/create")
    public String addUser(Model model, @ModelAttribute("todo") Todo todo) {
        todoRepository.save(todo);
        return "redirect:/todos";
    }

    //@Put mapping

    //@Delete mapping
}