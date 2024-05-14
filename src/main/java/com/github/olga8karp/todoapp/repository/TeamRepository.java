package com.github.olga8karp.todoapp.repository;

import com.github.olga8karp.todoapp.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository  extends JpaRepository<Team, Long> {
}
