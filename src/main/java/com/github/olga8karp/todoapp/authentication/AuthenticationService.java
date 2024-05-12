package com.github.olga8karp.todoapp.authentication;

import com.github.olga8karp.todoapp.entity.User;
import com.github.olga8karp.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserService userService;

    public AuthenticationService(com.github.olga8karp.todoapp.service.UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
