package com.github.olga8karp.todoapp.controller;

import com.github.olga8karp.todoapp.entity.Project;
import com.github.olga8karp.todoapp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(path = "/projects")
    public ResponseEntity<Project> createProject(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description
    ) {
        Project created = this.projectService.create(name, description);
        return ResponseEntity.ok(created);
    }

    @PutMapping(path = "/projects/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description
    ) {
        Project updated = this.projectService.update(id, name, description);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/projects/{id}")
    public ResponseEntity<Project> deleteProject(
            @PathVariable("id") Long id
    ) {
        this.projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/projects/{id}")
    public ResponseEntity<Project> findOneProject(
            @PathVariable("id") Long id
    ) {
        Project found = this.projectService.findOne(id);
        return ResponseEntity.ok(found);
    }

    @GetMapping(path = "/projects")
    public ResponseEntity<List<Project>> findAll(
    ) {
        List<Project> found = this.projectService.findAll();
        return ResponseEntity.ok(found);
    }

}
