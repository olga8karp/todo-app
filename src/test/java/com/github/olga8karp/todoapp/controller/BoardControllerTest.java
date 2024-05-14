package com.github.olga8karp.todoapp.controller;

import com.github.olga8karp.todoapp.exception.BoardNotFoundException;
import com.github.olga8karp.todoapp.exception.ProjectNotFoundException;
import com.github.olga8karp.todoapp.service.BoardService;
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

@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest {
    @MockBean
    private BoardService boardService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("admin")
    void createValidBoard_shouldReturn_status201() throws Exception {
        // Arrange
        var request = post("/boards")
                .param("projectId", "1")
                .param("name", "Test Board")
                .param("description", "Test Description")
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isCreated());
    }

    @Test
    @WithMockUser("admin")
    void createBoard_withoutProjectId_shouldReturn_status400() throws Exception {
        // Arrange
        var request = post("/boards")
                .param("projectId", (String) null)
                .param("name", "Test Name")
                .param("description", "Test Description")
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void createBoard_withNonExistentProjectId_shouldReturn_status400() throws Exception {
        // Arrange
        doThrow(new IllegalArgumentException("Project with id 0 not found"))
                .when(boardService).create(eq(0L), anyString(), anyString());

        var request = post("/boards")
                .param("projectId", "0")
                .param("name", "Test Name")
                .param("description", "Test Description")
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void createBoard_withoutName_shouldReturn_status400() throws Exception {
        // Arrange
        var request = post("/boards")
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
    void createBoard_withoutDescription_shouldReturn_status400() throws Exception {
        // Arrange
        var request = post("/boards")
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
    void updateBoard_shouldReturn_status200() throws Exception {
        // Arrange
        var request = put("/boards/{id}", 1L)
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
    void updateBoard_withoutName_shouldReturn_status400() throws Exception {
        // Arrange
        var request = put("/boards/{id}", 1L)
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
    void updateBoard_withoutDescription_shouldReturn_status400() throws Exception {
        // Arrange
        var request = put("/boards/{id}", 1L)
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
    void updateBoard_withNonExistentId_shouldReturn_status404() throws Exception {
        // Arrange
        when(boardService.update(eq(0L), anyString(), anyString()))
                .thenThrow(new BoardNotFoundException(0L));

        var request = put("/boards/{id}", 0L)
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
    void deleteBoard_shouldReturn_status204() throws Exception {
        // Arrange
        var request = delete("/boards/{id}", 1L)
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser("admin")
    void deleteBoard_withNonExistentId_shouldReturn_status404() throws Exception {
        // Arrange
        doThrow(new BoardNotFoundException(0L))
                .when(boardService).delete(eq(0L));

        var request = delete("/boards/{id}", 0L)
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser("admin")
    void findOneBoard_shouldReturn_status200() throws Exception {
        // Arrange
        var request = get("/boards/{id}", 1L)
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser("admin")
    void findOneBoard_withNonExistentId_shouldReturn_status404() throws Exception {
        // Arrange
        doThrow(new BoardNotFoundException(0L))
                .when(boardService).findOne(eq(0L));

        var request = get("/boards/{id}", 0L)
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
        var request = get("/boards")
                .with(csrf());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response.andExpect(status().isOk());
    }
}