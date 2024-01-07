package dev.iosenberg.todo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import dev.iosenberg.todo.models.Todo;
import dev.iosenberg.todo.repositories.TodoRepository;

@Controller
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @GetMapping
    public String getAllTodos(Model model, @ModelAttribute Todo todo) {
        List<Todo> todos = todoRepository.findAll();

        model.addAttribute("todos", todos);
        model.addAttribute("todo", new Todo());
        return "display-todos";
    }

    //Temp, for debug purposes
    @GetMapping("/{id}")
    public String getTodo(Model model, @PathVariable("id") Long id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);

        if(todoOptional.isPresent()) {
            List<Todo> todos = new ArrayList<Todo>();
            todos.add(todoOptional.get());

            model.addAttribute("todos", todos);
            model.addAttribute("todo", new Todo());

            return "display-todos";
        }
        else {
            return "redirect:/todo";
        }
    }
    
    @PostMapping("/create")
    public String addTodo(Model model, @ModelAttribute("todo") Todo todo) {
        todoRepository.save(todo);
        return "redirect:/todo";
    }

    //Mark complete
    @GetMapping("/complete/{id}")
    public String completeTodo(@PathVariable("id") long id) {
        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        todo.setCompleted(true);
        todoRepository.save(todo);

        return "redirect:/todo";
    }

    @PostMapping("/update/{id}")
    public String updateTodo(@PathVariable("id") long id, Model model, Todo todo) {
        todoRepository.save(todo);
        return "redirect:/todo";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") long id) {
        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

            todoRepository.delete(todo);

            return "redirect:/todo";
    }
}