package com.github.olga8karp.todoapp.repository;

import com.github.olga8karp.todoapp.entity.TodoTask;
import com.github.olga8karp.todoapp.entity.User;
import com.github.olga8karp.todoapp.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoTaskRepository extends JpaRepository<TodoTask, Integer> {
    public Optional<TodoTask> findTodoTaskByTitle(String title);
    public Optional<TodoTask> findTodoTaskByUser(User user);
    public Optional<TodoTask> findTodoTaskByUserAndStatus(User user, Status status);
}
