package com.project.userservice.service;

import com.project.userservice.DTO.*;

public interface UserService {
    AuthResponseDTO register(UserRegisterDTO dto);

    AuthResponseDTO login(LoginRequestDTO dto);

    UserProfileDTO getUserProfile(String email);

    UserProfileDTO updateUsername(String email, String newUsername);

    BookingHistoryResponseDTO getBookingHistoryWithPayments(String email);

    BookingHistoryResponseDTO getBookingHistoryWithPaymentsByEmail(String email);

    BookingHistoryResponseDTO getBookingHistoryWithPaymentsByUserId(String userId);
}