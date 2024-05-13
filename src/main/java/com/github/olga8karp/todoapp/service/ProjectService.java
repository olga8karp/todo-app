package com.github.olga8karp.todoapp.service;

import com.github.olga8karp.todoapp.entity.Project;
import com.github.olga8karp.todoapp.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project create(String name, String description) {
        Project toCreate = new Project();
        toCreate.setName(name);
        toCreate.setDescription(description);
        return projectRepository.save(toCreate);
    }

    public Project update(Long id, String name, String description) {
        Project toUpdate = findOne(id);
        toUpdate.setName(name);
        toUpdate.setDescription(description);
        return projectRepository.save(toUpdate);
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    public Project findOne(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found: " + id));
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

}
