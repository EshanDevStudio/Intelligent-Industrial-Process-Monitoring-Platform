package com.eshan.backend.security.service;

import com.eshan.backend.dto.AuthResponse;
import com.eshan.backend.dto.ErrorResponse;
import com.eshan.backend.dto.LoginRequest;
import com.eshan.backend.dto.RegisterRequest;
import com.eshan.backend.user.entity.User;
import com.eshan.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            return ResponseEntity.ok(new AuthResponse("JWT_token", "user_name"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }

    }
}
