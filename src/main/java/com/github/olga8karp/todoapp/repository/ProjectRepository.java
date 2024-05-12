package com.github.olga8karp.todoapp.repository;

import com.github.olga8karp.todoapp.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}