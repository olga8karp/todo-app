package com.github.olga8karp.todoapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(Long id) {
        super("Board with id " + id + " not found");
    }
}
