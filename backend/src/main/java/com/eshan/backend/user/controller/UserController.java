package com.eshan.backend.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/user/data")
    public String getData() {
        return "success: user data";
    }

    @GetMapping("/api/moderator/role")
    public String getRole() {
        return "Role success";
    }

}
