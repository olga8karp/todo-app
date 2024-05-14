package com.github.olga8karp.todoapp.service;

import com.github.olga8karp.todoapp.entity.Team;
import com.github.olga8karp.todoapp.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team create(String name, String description) {
        Team toCreate = new Team();
        toCreate.setName(name);
        toCreate.setDescription(description);
        return teamRepository.save(toCreate);
    }

    public Team update(Long id, String name, String description) {
        Team toUpdate = findOne(id);
        toUpdate.setName(name);
        toUpdate.setDescription(description);
        return teamRepository.save(toUpdate);
    }

    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

    public Team findOne(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found: " + id));
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }
}
