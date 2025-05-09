package com.github.olga8karp.todoapp.service;

import com.github.olga8karp.todoapp.entity.Project;
import com.github.olga8karp.todoapp.exception.ProjectNotFoundException;
import com.github.olga8karp.todoapp.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = ProjectServiceTest.Configuration.class)
@ActiveProfiles("it")
class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    void createProject_shouldReturnCreatedProject() {
        // Arrange

        // Act
        var created = projectService.create("Test Name", "Test Description");

        // Assert
        assertNotNull(created.getId());
    }

    @Test
    @Sql("insert-one-project.sql")
    void updateProject_shouldReturnUpdatedProject() {
        var updated = projectService.update(1000L, "Updated Name", "Updated Description");

        assertEquals("Updated Name", updated.getName());
        assertEquals("Updated Description", updated.getDescription());
    }

    @Test
    @Sql("insert-one-project.sql")
    void updateProject_withNonExistentId_shouldThrowException() {
        Executable tryUpdate = () -> projectService.update(100L, "Updated Name", "Updated Description");

        assertThrows(ProjectNotFoundException.class, tryUpdate);
    }

    @Test
    @Sql("insert-one-project.sql")
    void deleteProject_shouldSucceed() {
        Executable tryDelete = () -> projectService.delete(1000L);

        assertDoesNotThrow(tryDelete);
    }

    @Test
    @Sql("insert-one-project.sql")
    void deleteProject_withNonExistentId_shouldThrowException() {
        Executable tryDelete = () -> projectService.delete(100L);

        assertThrows(ProjectNotFoundException.class, tryDelete);
    }

    @Test
    @Sql("insert-one-project.sql")
    void findOne_shouldReturnProject() {
        // Arrange

        // Act
        var found = projectService.findOne(1000L);

        // Assert
        assertNotNull(found.getId());
    }

    @Test
    @Sql("insert-one-project.sql")
    void findOne_withNonExistentId_shouldThrowException() {
        // Arrange

        // Act
        Executable tryFind = () -> projectService.findOne(100L);

        // Assert
        assertThrows(ProjectNotFoundException.class, tryFind);
    }

    @Test
    @Sql("insert-10-projects.sql")
    void findAll_shouldReturnAllProjects() {
        // Arrange

        // Act
        var found = projectService.findAll();

        // Assert
        assertEquals(10, found.size());
    }

    @Test
    @Sql("insert-10-projects.sql")
    void findAll_shouldReturnProjectsFromSpecificPage() {
        // Arrange

        // Act
        var found = projectService.findAll(PageRequest.of(1, 2));

        // Assert
        assertAll(
                () -> assertEquals(10, found.getTotalElements()),
                () -> assertEquals(5, found.getTotalPages()),
                () -> assertEquals(2, found.getContent().size()),
                () -> assertEquals(1003L, found.getContent().getFirst().getId())
        );
    }

    @Test
    @Sql("insert-10-projects.sql")
    void findAll_shouldReturnProjectsInSortedOrder() {
        // Arrange

        // Act
        var found = projectService.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));

        // Assert
        assertAll(
                () -> assertEquals(10, found.getTotalElements()),
                () -> assertEquals(1, found.getTotalPages()),
                () -> assertEquals(10, found.getContent().size()),
                () -> assertEquals(1010L, found.getContent().getFirst().getId()),
                () -> assertThat(found.getContent()).isSortedAccordingTo(Comparator.comparing(Project::getName))
        );
    }

    @TestConfiguration(proxyBeanMethods = false)
    static class Configuration {

        @Bean
        ProjectService projectService(ProjectRepository projectRepository) {
            return new ProjectService(projectRepository);
        }
    }
}