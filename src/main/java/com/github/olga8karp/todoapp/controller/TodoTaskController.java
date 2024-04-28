package com.github.olga8karp.todoapp.controller;

import com.github.olga8karp.todoapp.entity.TodoTask;
import com.github.olga8karp.todoapp.service.TodoTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoTaskController {

    private final TodoTaskService todoTaskService;

    @GetMapping("/tasks")
    public List<TodoTask> findAll() {
        return todoTaskService.findAll();
    }

}
