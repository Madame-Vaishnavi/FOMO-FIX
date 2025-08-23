package com.project.userservice.service;

import com.project.userservice.DTO.*;
import com.project.userservice.exceptions.*;
import com.project.userservice.model.User;
import com.project.userservice.repository.UserRepository;
import com.project.userservice.JWT.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthResponseDTO register(UserRegisterDTO dto) {
        if (repo.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists!");
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUsername(dto.getUsername());
        user.setRole("USER");
        repo.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponseDTO(token, user.getId());
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO dto) {
        User user = repo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new UserNotFoundException("Invalid credentials");

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponseDTO(token, user.getId());
    }

    @Override
    public UserProfileDTO getUserProfile(String email) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserProfileDTO profile = new UserProfileDTO();
        profile.setId(user.getId());
        profile.setEmail(user.getEmail());
        profile.setUsername(user.getUsername());
        profile.setRole(user.getRole());
        return profile;
    }
}
