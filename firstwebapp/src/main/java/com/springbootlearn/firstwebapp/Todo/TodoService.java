package com.springbootlearn.firstwebapp.Todo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Service
public class TodoService {
    //update
    //delete
    //add new one

    private static final List<Todo> todos = new ArrayList<>();

    private static int count = 1;

    static {
        todos.add(new Todo(count++, "Doaa",
                "learn DBMS", LocalDate.now().plusMonths(2), false));

        todos.add(new Todo(count++, "Doaa",
                "learn Java", LocalDate.now().plusMonths(2), true));

        todos.add(new Todo(count++, "Doaa",
                "learn SQL", LocalDate.now().plusMonths(2), false));
    }

    public List<Todo> findByUsername(String username) {
        return todos;
    }

    public void addTodo(String username, String description, LocalDate targetDate, boolean done) {
        todos.add(new Todo(count++, username, description, targetDate, done));
    }

    public void deleteTodoById(int id) {
        /*predicate to remove todo with the matching id*/
        Predicate<? super Todo> predicate = todo -> todo.getId() == id;
        todos.removeIf(predicate);
    }

    public void updateTodoById(int id, String description) {
        Predicate<? super Todo> predicate = todo -> todo.getId() == id;
        todos.stream().filter(predicate).findFirst().get().setDescription(description);
//        todos.get(id).setDescription(description);
    }


    public Todo findById(int id) {
        Predicate<? super Todo> predicate = todo -> todo.getId() == id;
        return todos.stream().filter(predicate).findFirst().get();
    }
}
