package com.marktech.task_management_system.controller;

import com.marktech.task_management_system.model.User;
import com.marktech.task_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return service.login(user);
    }
}
