package com.github.olga8karp.todoapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.olga8karp.todoapp.entity.enums.Priority;
import com.github.olga8karp.todoapp.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TodoTask")
@Table(name = "todo_task")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateInitializer", "user"})
public class TodoTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Status status;
    private Priority priority;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public TodoTask(String title, String description, LocalDate deadline, Priority priority, Status status, User user) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.user = user;
    }

}
