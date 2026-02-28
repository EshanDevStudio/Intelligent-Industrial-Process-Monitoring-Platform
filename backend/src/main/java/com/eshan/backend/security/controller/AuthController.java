package com.eshan.backend.security.controller;

import com.eshan.backend.user.dto.ErrorResponse;
import com.eshan.backend.user.dto.RegisterRequest;
import com.eshan.backend.user.dto.RegisterResponse;
import com.eshan.backend.user.entity.User;
import com.eshan.backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.registerUser(registerRequest);

            RegisterResponse response = new RegisterResponse(
                    "User registered successfully",
                    user.getUsername(),
                    user.getEmail()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}
