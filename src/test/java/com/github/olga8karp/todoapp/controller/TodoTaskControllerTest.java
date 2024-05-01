package com.github.olga8karp.todoapp.controller;

import com.github.olga8karp.todoapp.entity.TodoTask;
import com.github.olga8karp.todoapp.service.TodoTaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
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
}
