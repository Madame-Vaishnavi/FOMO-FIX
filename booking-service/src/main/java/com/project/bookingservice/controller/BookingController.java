package com.project.bookingservice.controller;

import com.project.bookingservice.dto.BookingRequestDTO;
import com.project.bookingservice.dto.BookingResponseDTO;
import com.project.bookingservice.model.Booking;
import com.project.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable int id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingByUser(@PathVariable String email) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(email));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingByEvent(@PathVariable int eventId) {
        return ResponseEntity.ok(bookingService.getBookingsByEvent(eventId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
