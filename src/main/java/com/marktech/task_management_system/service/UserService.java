package com.marktech.task_management_system.service;

import com.marktech.task_management_system.config.service.JwtService;
import com.marktech.task_management_system.model.User;
import com.marktech.task_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager manager;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ResponseEntity<String> register(User user){
        // encoding the password
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successfull");
    }

    public String login(User user) {

        Authentication authentication =
                manager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        //Checking if User is present in Database or Not
        if(authentication.isAuthenticated()){

            return jwtService.generateToken(user.getEmail());
        }
        return "User Authentication Fail";
    }
}
