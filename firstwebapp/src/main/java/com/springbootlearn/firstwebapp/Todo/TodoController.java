package com.springbootlearn.firstwebapp.Todo;


import jakarta.validation.Valid;
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
public class TodoController {


    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(value = "/list-todos")
    public String getListOfTodos(ModelMap model) {
        model.addAttribute("todos", todoService.findByUsername("doaa"));
        return "listTodos";
//        return todoService.findByUsername("doaa");
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model) {
        String name = (String) model.get("name");
        Todo todo = new Todo(0, name, "", LocalDate.now().plusYears(1), false);
        model.put("todo", todo);
        return "todo";
    }

//    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
//    public String addNewTodo(@RequestParam String description, ModelMap model) {
//        String name = (String) model.get("name");
//        todoService.addTodo(name, description, LocalDate.now().plusYears(1), false);
//        return "redirect:list-todos";
//    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addNewTodo(@Valid Todo todo, BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            return "todo";
        }

        String name = (String) model.get("name");

        todoService.addTodo(name, todo.getDescription(), todo.getTargetDate(), todo.isDone());

        return "redirect:list-todos";
    }

    @RequestMapping(value = "delete-todo")
    public String deleteTodo(@RequestParam int id) {
        todoService.deleteTodoById(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
        Todo todo = todoService.findById(id);
        model.addAttribute("todo", todo);
        return "todo";
    }

    @RequestMapping(value = "update-todo", method = RequestMethod.POST)
    public String updateTodoById(ModelMap model, @RequestParam int id, @RequestParam String description, @RequestParam LocalDate targetDate, @Valid Todo todo, BindingResult result) {

        if (result.hasErrors()) {
            return "todo";
        }
        String username = (String) model.get("name");
        todo.setUserName(username);

        todoService.updateTodoById(id, description, targetDate);
        return "redirect:list-todos";
    }

}
