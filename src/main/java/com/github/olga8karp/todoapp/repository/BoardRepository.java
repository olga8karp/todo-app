package com.github.olga8karp.todoapp.repository;

import com.github.olga8karp.todoapp.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
