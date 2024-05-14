package com.github.olga8karp.todoapp.controller;

import com.github.olga8karp.todoapp.entity.Board;
import com.github.olga8karp.todoapp.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class BoardController {
    private final BoardService boardService;

    @PostMapping(path = "/boards")
    public ResponseEntity<Board> createBoard(
            @RequestParam(value = "projectId") Long projectId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description
    ) {
        Board created = this.boardService.create(projectId, name, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(path = "/boards/{id}")
    public ResponseEntity<Board> updateBoard(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description
    ) {
        Board updated = this.boardService.update(id, name, description);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/boards/{id}")
    public ResponseEntity<Board> deleteBoard(
            @PathVariable("id") Long id
    ) {
        this.boardService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/boards/{id}")
    public ResponseEntity<Board> findOneBoard(
            @PathVariable("id") Long id
    ) {
        Board found = this.boardService.findOne(id);
        return ResponseEntity.ok(found);
    }

    @GetMapping(path = "/boards")
    public ResponseEntity<List<Board>> findAll(
    ) {
        List<Board> found = this.boardService.findAll();
        return ResponseEntity.ok(found);
    }

}
