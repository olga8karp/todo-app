package com.github.olga8karp.todoapp.controller;

import com.github.olga8karp.todoapp.entity.TodoTask;
import com.github.olga8karp.todoapp.entity.enums.Priority;
import com.github.olga8karp.todoapp.entity.enums.Status;
import com.github.olga8karp.todoapp.service.TodoTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TodoTaskControllerTest {
    @MockBean
    private TodoTaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findTodoTasks_shouldReturnTaskList() throws Exception {
        List<TodoTask> tasksList = List.of(new TodoTask(), new TodoTask());
        when(taskService.findAll()).thenReturn(tasksList);
        mockMvc.perform(get("/tasks")).andExpect(status().isOk());
    }

    @Test
    public void findTaskById_shouldReturnTask() throws Exception {
        TodoTask task = new TodoTask();
        when(taskService.findTaskById(1)).thenReturn(task);
        var request = get("/tasks/getById")
                .param("id", String.valueOf(1));

        var response = mockMvc.perform(request);

        response.andExpect(status().isOk());
    }

    @Test
    public void findTaskByUser_shouldReturnTask() throws Exception {
        TodoTask task = new TodoTask();
        int userId = 1;
        when(taskService.findTodoTaskByUserAndStatus(userId, null)).thenReturn(task);
        var request = get("/tasks/getByUserAndStatus")
                .param("userId", String.valueOf(userId));

        var response = mockMvc.perform(request);

        response.andExpect(status().isOk());
    }

    @Test
    public void createTask_shouldReturnTask() throws Exception {
        TodoTask task = new TodoTask();
        String title = "Task Title";
        String description = "Task Description";
        LocalDate deadline = LocalDate.now();
        Priority priority = Priority.HIGH;
        Status status = Status.IN_PROGRESS;
        int userId = 1;
        when(taskService.addTask(title, description, deadline, priority, status, userId)).thenReturn(task);
        var request = post("/tasks/create")
                .param("title", title)
                .param("description", description)
                .param("deadline", deadline.toString())
                .param("priority", priority.toString())
                .param("status", status.toString())
                .param("userId", String.valueOf(userId));

        var response = mockMvc.perform(request);

        response.andExpect(status().isOk());
    }

    @Test
    public void updateTask_shouldReturnTask() throws Exception {
        TodoTask updatedTask = new TodoTask();
        int taskId = 1;
        String newTitle = "New Task Title";
        when(taskService.updateTitleById(taskId, newTitle)).thenReturn(updatedTask);
        var request = post("/tasks/update")
                .param("id", String.valueOf(taskId))
                .param("newTitle", newTitle);

        var response = mockMvc.perform(request);

        response.andExpect(status().isOk());
    }

    @Test
    public void deleteTask_shouldReturnTask() throws Exception {
        int taskId = 1;
        doNothing().when(taskService).delete(taskId);
        var request = delete("/tasks/delete")
                .param("id", String.valueOf(taskId));

        var response = mockMvc.perform(request);

        response.andExpect(status().isNoContent());
    }
}
