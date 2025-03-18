package com.marktech.task_management_system.config.service;

import com.marktech.task_management_system.model.User;
import com.marktech.task_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repository.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrinciple(user);

    }
}
