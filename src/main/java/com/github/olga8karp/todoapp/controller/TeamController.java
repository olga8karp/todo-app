package com.github.olga8karp.todoapp.controller;

import com.github.olga8karp.todoapp.entity.Team;
import com.github.olga8karp.todoapp.service.TeamService;
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
public class TeamController {
    private final TeamService teamService;

    @PostMapping(path = "/teams")
    public ResponseEntity<Team> createTeam(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description
    ) {
        Team created = this.teamService.create(name, description);
        return ResponseEntity.ok(created);
    }

    @PutMapping(path = "/teams/{id}")
    public ResponseEntity<Team> updateTeam(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description
    ) {
        Team updated = this.teamService.update(id, name, description);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/teams/{id}")
    public ResponseEntity<Team> deleteTeam(
            @PathVariable("id") Long id
    ) {
        this.teamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/teams/{id}")
    public ResponseEntity<Team> findOneTeam(
            @PathVariable("id") Long id
    ) {
        Team found = this.teamService.findOne(id);
        return ResponseEntity.ok(found);
    }

    @GetMapping(path = "/teams")
    public ResponseEntity<List<Team>> findAll(
    ) {
        List<Team> found = this.teamService.findAll();
        return ResponseEntity.ok(found);
    }

}
