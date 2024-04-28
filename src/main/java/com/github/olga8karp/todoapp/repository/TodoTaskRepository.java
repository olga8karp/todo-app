package com.github.olga8karp.todoapp.repository;

import com.github.olga8karp.todoapp.entity.TodoTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoTaskRepository extends JpaRepository<TodoTask, Integer> {

}
