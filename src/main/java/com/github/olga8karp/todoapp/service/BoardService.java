package com.github.olga8karp.todoapp.service;

import com.github.olga8karp.todoapp.entity.Board;
import com.github.olga8karp.todoapp.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board create(String name, String description) {
        Board toCreate = new Board();
        toCreate.setName(name);
        toCreate.setDescription(description);
        return boardRepository.save(toCreate);
    }

    public Board update(Long id, String name, String description) {
        Board toUpdate = findOne(id);
        toUpdate.setName(name);
        toUpdate.setDescription(description);
        return boardRepository.save(toUpdate);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Board findOne(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found: " + id));
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}
