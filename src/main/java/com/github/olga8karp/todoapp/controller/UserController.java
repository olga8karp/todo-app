package com.github.olga8karp.todoapp.controller;

import com.github.olga8karp.todoapp.entity.TodoTask;
import com.github.olga8karp.todoapp.entity.User;
import com.github.olga8karp.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/create")
    public ResponseEntity<?> createUser(@RequestParam("name") String name,
                                        @RequestParam("email") String email,
                                        @RequestParam("email") String userName,
                                        @RequestParam("password") String password){
        return ResponseEntity.ok(userService.createUser(name, email, userName, password).toString());
    }

    @GetMapping("/users/getById")
    public ResponseEntity<User> getUserById(@RequestParam("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
