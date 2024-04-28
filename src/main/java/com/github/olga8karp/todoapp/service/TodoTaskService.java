package com.github.olga8karp.todoapp.service;

import com.github.olga8karp.todoapp.entity.TodoTask;
import com.github.olga8karp.todoapp.repository.TodoTaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoTaskService {

    private final TodoTaskRepository todoTaskRepository;

    public List<TodoTask> findAll() {
        return todoTaskRepository.findAll();
    }

}
