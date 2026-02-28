package com.eshan.backend.user.service;

import com.eshan.backend.user.dto.RegisterRequest;
import com.eshan.backend.user.entity.User;
import com.eshan.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest registerRequest) {

        //Check if username exists
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        //Check if email exists
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        //Create new user
        User user =  new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword())
        );

        //Assign default role
        user.getRoles().add("USER");

        return userRepository.save(user);
    }
}
