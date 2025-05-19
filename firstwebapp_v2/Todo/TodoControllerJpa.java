package com.springbootlearn.firstwebapp.Todo;


import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {


    private final TodoService todoService;
    private TodoRepository todoRepository;

    public TodoControllerJpa(TodoService todoService, TodoRepository todoRepository) {
        this.todoService = todoService;
        this.todoRepository = todoRepository;
    }

    @RequestMapping(value = "/list-todos")
    public String getListOfTodos(ModelMap model) {
        String name = getLoggedInUsername();

        model.addAttribute("todos", todoRepository.findByUserName(name));
        return "listTodos";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model) {
        String name = getLoggedInUsername();
        Todo todo = new Todo(0, name, "", LocalDate.now().plusYears(1), false);
        model.put("todo", todo);
        return "todo";
    }


    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addNewTodo(@Valid Todo todo, BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            return "todo";
        }

        String name = getLoggedInUsername();
        todo.setUserName(name);

        todoRepository.save(todo);
//        todoService.addTodo(name, todo.getDescription(), todo.getTargetDate(), todo.isDone());

        return "redirect:list-todos";
    }

    @RequestMapping(value = "delete-todo")
    public String deleteTodo(@RequestParam int id) {
//        todoService.deleteTodoById(id);
        todoRepository.deleteById(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
//        Todo todo = todoService.findById(id);
        Todo todo = todoRepository.findById(id).get();
        model.addAttribute("todo", todo);
        return "todo";
    }

    @RequestMapping(value = "update-todo", method = RequestMethod.POST)
    public String updateTodoById(ModelMap model, @RequestParam int id, @RequestParam String description, @RequestParam LocalDate targetDate, @Valid Todo todo, BindingResult result) {

        if (result.hasErrors()) {
            return "todo";
        }
        String username = getLoggedInUsername();
        todo.setUserName(username);

//        todoService.updateTodoById(id, description, targetDate);
        todoRepository.save(todo);
        return "redirect:list-todos";
    }

    private String getLoggedInUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
