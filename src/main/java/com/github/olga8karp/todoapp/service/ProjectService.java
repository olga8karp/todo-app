package com.github.olga8karp.todoapp.service;

import com.github.olga8karp.todoapp.entity.Project;
import com.github.olga8karp.todoapp.exception.ProjectNotFoundException;
import com.github.olga8karp.todoapp.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException(id);
        }
        projectRepository.deleteById(id);
    }

    public Project findOne(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

}
