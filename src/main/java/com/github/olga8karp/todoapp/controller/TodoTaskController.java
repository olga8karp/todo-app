package com.github.olga8karp.todoapp.controller;

import com.github.olga8karp.todoapp.entity.TodoTask;
import com.github.olga8karp.todoapp.entity.enums.Priority;
import com.github.olga8karp.todoapp.entity.enums.Status;
import com.github.olga8karp.todoapp.service.TodoTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoTaskController {

    private final TodoTaskService todoTaskService;

    @GetMapping("/tasks")
    public List<TodoTask> findAllTodoTasks() {
        return todoTaskService.findAll();
    }

    @GetMapping("/tasks/getById")
    public TodoTask getTodoTaskById(@RequestParam int id) {
        return todoTaskService.findTaskById(id);
    }

    @GetMapping("/tasks/getByTitle")
    public TodoTask getTodoTaskByTitle(@RequestParam String title) {
        return todoTaskService.findTodoTaskByTitle(title);
    }

    @GetMapping("/tasks/getByUser")
    public TodoTask getTodoTaskByUser(@RequestParam int userId, @RequestParam(required=false) Status status) {
        return todoTaskService.findTodoTaskByUserAndStatus(userId, status);
    }

    @PostMapping("/tasks/create")
    public ResponseEntity<TodoTask> createTodoTask(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("deadline") LocalDate deadline,
            @RequestParam("priority") Priority priority,
            @RequestParam("status") Status status,
            @RequestParam("userId") int userId
    ) {
        TodoTask task = todoTaskService.addTask(title, description, deadline, priority, status, userId);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/tasks/update")
    public ResponseEntity<String> updateTodoTask(@RequestParam("id") int id,
                                                 @RequestParam(value = "title", required = false) String title,
                                                 @RequestParam(value = "description", required = false) String description) {
        TodoTask task = null;

        if (title != null) {
            task = todoTaskService.updateTitleById(id, title);
        }

        if (description != null) {
            task = todoTaskService.updateDescriptionById(id, description);
        }

        if (task == null) {
            return ResponseEntity.ok("Nothing to update");
        }

        return ResponseEntity.ok(task.toString());
    }

    @DeleteMapping("/tasks/delete")
    public ResponseEntity<String> deleteTodoTask(@RequestParam(value = "id") int id) {
        todoTaskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
