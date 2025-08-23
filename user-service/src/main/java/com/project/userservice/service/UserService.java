package com.project.userservice.service;

import com.project.userservice.DTO.*;

public interface UserService {
    AuthResponseDTO register(UserRegisterDTO dto);

    AuthResponseDTO login(LoginRequestDTO dto);

    UserProfileDTO getUserProfile(String email);
}