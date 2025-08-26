package com.project.userservice.service;

import com.project.userservice.DTO.*;
import com.project.userservice.exceptions.*;
import com.project.userservice.model.User;
import com.project.userservice.repository.UserRepository;
import com.project.userservice.JWT.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${booking.service.url:http://localhost:8081}")
    private String bookingServiceUrl;

    @Value("${payment.service.url:http://localhost:8083}")
    private String paymentServiceUrl;

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
        user.setName(dto.getUsername()); // Set name to username for backward compatibility
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

        // Handle legacy users who might not have username set
        if (user.getUsername() == null && user.getName() != null) {
            user.setUsername(user.getName());
            repo.save(user);
        }

        UserProfileDTO profile = new UserProfileDTO();
        profile.setId(user.getId());
        profile.setEmail(user.getEmail());
        profile.setUsername(user.getUsername());
        profile.setRole(user.getRole());
        return profile;
    }

    @Override
    public UserProfileDTO updateUsername(String email, String newUsername) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if username is already taken
        if (repo.existsByUsername(newUsername)
                && (user.getUsername() == null || !user.getUsername().equals(newUsername))) {
            throw new UserAlreadyExistsException("Username already exists!");
        }

        user.setUsername(newUsername);
        user.setName(newUsername); // Update name field for backward compatibility
        repo.save(user);

        UserProfileDTO profile = new UserProfileDTO();
        profile.setId(user.getId());
        profile.setEmail(user.getEmail());
        profile.setUsername(user.getUsername());
        profile.setRole(user.getRole());
        return profile;
    }

    @Override
    public BookingHistoryResponseDTO getBookingHistoryWithPayments(String email) {
        try {
            // Get bookings from booking service
            String bookingUrl = bookingServiceUrl + "/api/bookings/user/" + email;
            System.out.println("Calling booking service at: " + bookingUrl);

            BookingResponseDTO[] bookings = restTemplate.getForObject(bookingUrl, BookingResponseDTO[].class);

            if (bookings == null) {
                System.out.println("No bookings found for user: " + email);
                return new BookingHistoryResponseDTO(List.of());
            }

            List<BookingHistoryResponseDTO.BookingWithPaymentDTO> bookingWithPayments = List.of(bookings).stream()
                    .map(booking -> {
                        // Get payment info for each booking
                        String paymentUrl = paymentServiceUrl + "/api/payments/booking/" + booking.getBookingId();
                        System.out.println("Calling payment service at: " + paymentUrl);
                        PaymentResponseDTO payment = null;
                        try {
                            payment = restTemplate.getForObject(paymentUrl, PaymentResponseDTO.class);
                        } catch (Exception e) {
                            // Payment might not exist for this booking
                            System.out.println(
                                    "No payment found for booking " + booking.getBookingId() + ": " + e.getMessage());
                        }

                        BookingHistoryResponseDTO.PaymentInfoDTO paymentInfo = null;
                        if (payment != null) {
                            paymentInfo = new BookingHistoryResponseDTO.PaymentInfoDTO(
                                    payment.getPaymentId(),
                                    payment.getAmount(),
                                    payment.getPaymentMode() != null ? payment.getPaymentMode().toString() : null,
                                    payment.getStatus() != null ? payment.getStatus().toString() : null,
                                    payment.getTransactionId(),
                                    payment.getPaymentDate());
                        }

                        return new BookingHistoryResponseDTO.BookingWithPaymentDTO(
                                booking.getBookingId(),
                                booking.getEventId(),
                                booking.getEventName(),
                                booking.getCategoryName(),
                                booking.getSeatBooked(),
                                booking.getPrice(),
                                booking.getBookingTime(),
                                booking.getStatus(),
                                paymentInfo);
                    }).collect(Collectors.toList());

            return new BookingHistoryResponseDTO(bookingWithPayments);
        } catch (Exception e) {
            System.err.println("Error fetching booking history for user " + email + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching booking history: " + e.getMessage());
        }
    }

    @Override
    public BookingHistoryResponseDTO getBookingHistoryWithPaymentsByEmail(String email) {
        return getBookingHistoryWithPayments(email);
    }

    @Override
    public BookingHistoryResponseDTO getBookingHistoryWithPaymentsByUserId(String userId) {
        try {
            String bookingUrl = bookingServiceUrl + "/api/bookings/user-id/" + userId;
            System.out.println("Calling booking service at: " + bookingUrl);

            BookingResponseDTO[] bookings = restTemplate.getForObject(bookingUrl, BookingResponseDTO[].class);

            if (bookings == null) {
                System.out.println("No bookings found for userId: " + userId);
                return new BookingHistoryResponseDTO(List.of());
            }

            List<BookingHistoryResponseDTO.BookingWithPaymentDTO> bookingWithPayments = List.of(bookings).stream()
                    .map(booking -> {
                        String paymentUrl = paymentServiceUrl + "/api/payments/booking/" + booking.getBookingId();
                        System.out.println("Calling payment service at: " + paymentUrl);
                        PaymentResponseDTO payment = null;
                        try {
                            payment = restTemplate.getForObject(paymentUrl, PaymentResponseDTO.class);
                        } catch (Exception e) {
                            System.out.println(
                                    "No payment found for booking " + booking.getBookingId() + ": " + e.getMessage());
                        }

                        BookingHistoryResponseDTO.PaymentInfoDTO paymentInfo = null;
                        if (payment != null) {
                            paymentInfo = new BookingHistoryResponseDTO.PaymentInfoDTO(
                                    payment.getPaymentId(),
                                    payment.getAmount(),
                                    payment.getPaymentMode() != null ? payment.getPaymentMode().toString() : null,
                                    payment.getStatus() != null ? payment.getStatus().toString() : null,
                                    payment.getTransactionId(),
                                    payment.getPaymentDate());
                        }

                        return new BookingHistoryResponseDTO.BookingWithPaymentDTO(
                                booking.getBookingId(),
                                booking.getEventId(),
                                booking.getEventName(),
                                booking.getCategoryName(),
                                booking.getSeatBooked(),
                                booking.getPrice(),
                                booking.getBookingTime(),
                                booking.getStatus(),
                                paymentInfo);
                    }).collect(Collectors.toList());

            return new BookingHistoryResponseDTO(bookingWithPayments);
        } catch (Exception e) {
            System.err.println("Error fetching booking history for userId " + userId + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching booking history: " + e.getMessage());
        }
    }
}
