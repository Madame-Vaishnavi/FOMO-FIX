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

    @PatchMapping("/username")
    public UserProfileDTO updateUsername(Authentication authentication, @RequestBody UsernameUpdateDTO dto) {
        String email = (String) authentication.getPrincipal();
        return service.updateUsername(email, dto.getNewUsername());
    }

    @GetMapping("/booking-history")
    public BookingHistoryResponseDTO getBookingHistory(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return service.getBookingHistoryWithPayments(email);
    }

    // New: booking history by userId (no auth)
    @GetMapping("/booking-history/by-userId")
    public BookingHistoryResponseDTO getBookingHistoryByUserId(@RequestParam String userId) {
        return service.getBookingHistoryWithPaymentsByUserId(userId);
    }
}
