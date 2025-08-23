package com.project.userservice.controller;

import com.project.userservice.DTO.*;
import com.project.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody UserRegisterDTO dto) {
        return service.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return service.login(dto);
    }

    @GetMapping("/profile")
    public UserProfileDTO profile(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return service.getUserProfile(email);
    }
}
