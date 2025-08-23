package com.project.bookingservice.service;

import com.project.bookingservice.dto.BookingRequestDTO;
import com.project.bookingservice.dto.BookingResponseDTO;
import com.project.bookingservice.dto.EventResponseDTO;
import com.project.bookingservice.EventServiceClient;
import com.project.bookingservice.model.Booking;
import com.project.bookingservice.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    EventServiceClient eventServiceClient;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public BookingResponseDTO createBooking(BookingRequestDTO request) {

        EventResponseDTO event = eventServiceClient.getEventById(request.getEventId());
        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }
        EventResponseDTO.SeatCategoryDTO category = event.getSeatCategory().stream().filter(
                cat -> cat.getCategoryName().equalsIgnoreCase(request.getCategoryName())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Seat Category not found"));

        if (category.getAvailableSeats() < request.getSeatsRequested()) {
            throw new IllegalArgumentException("Not enough available seats");
        }

        double totalPrice = request.getSeatsRequested() * category.getPricePerSeat();
        Booking booking = new Booking();
        booking.setEventId(event.getId());
        booking.setEventName(event.getName());
        booking.setCategoryName(category.getCategoryName());
        booking.setSeatBooked(request.getSeatsRequested());
        booking.setPrice(totalPrice);
        booking.setUserEmail(request.getUserEmail());
        booking.setUserId(request.getUserId());
        booking.setBookingtime(LocalDateTime.now());
        booking.setStatus("CONFIRMED");

        Booking saved = bookingRepository.save(booking);

        Map<String, Object> bookingEvent = new HashMap<>();
        bookingEvent.put("bookingId", saved.getId());
        bookingEvent.put("eventId", event.getId());
        bookingEvent.put("categoryName", category.getCategoryName());
        bookingEvent.put("seatBooked", request.getSeatsRequested());
        bookingEvent.put("userEmail", request.getUserEmail());
        bookingEvent.put("totalAmount", totalPrice);

        kafkaTemplate.send("booking-created", bookingEvent);
        return toResponseDTO(saved);
    }

    public List<BookingResponseDTO> getBookingsByUser(String userEmail) {
        return bookingRepository.findByUserEmail(userEmail).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookingResponseDTO> getBookingsByUserId(String userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookingResponseDTO> getBookingsByEvent(int eventId) {
        return bookingRepository.findByEventId(eventId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BookingResponseDTO getBookingById(int id) {
        return bookingRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public void deleteBooking(int id) {
        bookingRepository.deleteById(id);
    }

    private BookingResponseDTO toResponseDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getId());
        dto.setEventId(booking.getEventId());
        dto.setEventName(booking.getEventName());
        dto.setCategoryName(booking.getCategoryName());
        dto.setSeatBooked(booking.getSeatBooked());
        dto.setPrice(booking.getPrice());
        dto.setUserEmail(booking.getUserEmail());
        dto.setUserId(booking.getUserId());
        dto.setBookingTime(booking.getBookingtime());
        dto.setStatus(booking.getStatus());
        return dto;
    }

}