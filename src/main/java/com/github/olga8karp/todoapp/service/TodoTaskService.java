package com.github.olga8karp.todoapp.service;

import com.github.olga8karp.todoapp.entity.TodoTask;
import com.github.olga8karp.todoapp.entity.User;
import com.github.olga8karp.todoapp.entity.enums.Priority;
import com.github.olga8karp.todoapp.entity.enums.Status;
import com.github.olga8karp.todoapp.repository.TodoTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoTaskService {

    private final TodoTaskRepository todoTaskRepository;
    private final UserService userService;

    public List<TodoTask> findAll() {
        return todoTaskRepository.findAll();
    }

    public TodoTask findTaskById(int id) {
        return todoTaskRepository.findById(id).orElse(null);
    }

    public TodoTask findTodoTaskByTitle(String title) {
        return todoTaskRepository.findTodoTaskByTitle(title).orElse(null);
    }

    public TodoTask findTodoTaskByUserAndStatus(int userId, Status status) {
        User user = userService.getUserById(userId);
        if (user == null) return null;
        if (status == null) {
            return todoTaskRepository.findTodoTaskByUser(user).orElse(null);
        } else {
            return todoTaskRepository.findTodoTaskByUserAndStatus(user, status).orElse(null);
        }
    }

    public TodoTask addTask(String title, String description, LocalDate deadline, Priority priority, Status status, int userId) {
        User user = userService.getUserById(userId);
        TodoTask task = new TodoTask(title, description, deadline, priority, status, user);
        user.getTasks().add(task);
        return todoTaskRepository.save(task);
    }

    public TodoTask updateTitleById(int id, String newTitle) {
        TodoTask task = findTaskById(id);
        if (task == null) return null;
        task.setTitle(newTitle);
        return todoTaskRepository.save(task);
    }

    public TodoTask updateDescriptionById(int id, String description) {
        TodoTask task = findTaskById(id);
        if (task == null) return null;
        task.setDescription(description);
        return todoTaskRepository.save(task);
    }

    public void delete(int id) {
        todoTaskRepository.deleteById(id);
    }
}
