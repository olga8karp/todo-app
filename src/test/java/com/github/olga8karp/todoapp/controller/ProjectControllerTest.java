package com.github.olga8karp.todoapp.controller;

import com.github.olga8karp.todoapp.exception.ProjectNotFoundException;
import com.github.olga8karp.todoapp.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProjectController.class)
class ProjectControllerTest {

    @MockBean
    private ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("admin")
    void createValidProject_shouldReturn_status201() throws Exception {
        // Arrange
        var request = post("/projects")
                .param("name", "Test Name")
                .param("description", "Test Description")
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isCreated());
    }

    @Test
    @WithMockUser("admin")
    void createProject_withoutName_shouldReturn_status400() throws Exception {
        // Arrange
        var request = post("/projects")
                .param("name", (String) null)
                .param("description", "Test Description")
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void createProject_withoutDescription_shouldReturn_status400() throws Exception {
        // Arrange
        var request = post("/projects")
                .param("name", "Test Name")
                .param("description", (String) null)
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void updateProject_shouldReturn_status200() throws Exception {
        // Arrange
        var request = put("/projects/{id}", 1L)
                .param("name", "Updated Name")
                .param("description", "Updated Description")
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser("admin")
    void updateProject_withoutName_shouldReturn_status400() throws Exception {
        // Arrange
        var request = put("/projects/{id}", 1L)
                .param("name", (String) null)
                .param("description", "Updated Description")
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void updateProject_withoutDescription_shouldReturn_status400() throws Exception {
        // Arrange
        var request = put("/projects/{id}", 1L)
                .param("name", "Updated Name")
                .param("description", (String) null)
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void updateProject_withNonExistentId_shouldReturn_status404() throws Exception {
        // Arrange
        when(projectService.update(eq(0L), anyString(), anyString()))
                .thenThrow(new ProjectNotFoundException(0L));

        var request = put("/projects/{id}", 0L)
                .param("name", "Updated Name")
                .param("description", "Updated Description")
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser("admin")
    void deleteProject_shouldReturn_status204() throws Exception {
        // Arrange
        var request = delete("/projects/{id}", 1L)
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser("admin")
    void deleteProject_withNonExistentId_shouldReturn_status404() throws Exception {
        // Arrange
        doThrow(new ProjectNotFoundException(0L))
                .when(projectService).delete(eq(0L));

        var request = delete("/projects/{id}", 0L)
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser("admin")
    void findOneProject_shouldReturn_status200() throws Exception {
        // Arrange
        var request = get("/projects/{id}", 1L)
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser("admin")
    void findOneProject_withNonExistentId_shouldReturn_status404() throws Exception {
        // Arrange
        doThrow(new ProjectNotFoundException(0L))
                .when(projectService).findOne(eq(0L));

        var request = get("/projects/{id}", 0L)
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser("admin")
    void findAll_shouldReturn_status200() throws Exception {
        // Arrange
        var request = get("/projects")
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isOk());
    }
}