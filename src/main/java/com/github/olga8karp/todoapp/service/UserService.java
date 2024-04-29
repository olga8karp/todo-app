package com.github.olga8karp.todoapp.service;

import com.github.olga8karp.todoapp.entity.User;
import com.github.olga8karp.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(String name, String email){
        return userRepository.save(new User(name, email));
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }
}
