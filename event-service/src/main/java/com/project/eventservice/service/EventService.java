package com.project.eventservice.service;

import com.project.eventservice.dto.EventRequestDTO;
import com.project.eventservice.dto.EventResponseDTO;
import com.project.eventservice.dto.SeatCategoryResponseDTO;
import com.project.eventservice.enums.EventCategory;
import com.project.eventservice.model.Event;
import com.project.eventservice.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // @Autowired
    // private KafkaTemplate<String, Object> kafkaTemplate;

    public EventResponseDTO createEvent(EventRequestDTO request) {
        Event event = new Event();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setDate(request.getDate());
        event.setCategory(request.getCategory());
        event.setImageUrl(request.getImageUrl());
        event.setSeatCategories(request.getSeatCategories().stream()
                .map(cat -> new Event.SeatCategory(
                        cat.getCategoryName(),
                        cat.getTotalSeats(),
                        cat.getPricePerSeat()))
                .collect(Collectors.toList()));
        Event saved = eventRepository.save(event);
        // kafkaTemplate.send("event-updates","EVENT CREATED",saved);
        return toResponseDTO(saved);
    }

    public EventResponseDTO getEventById(int id) {
        return eventRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void decrementAvailableSeats(int eventId, String categoryName, int seatsBooked) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        event.getSeatCategories().stream()
                .filter(cat -> cat.getCategoryName().equalsIgnoreCase(categoryName))
                .findFirst()
                .ifPresent(cat -> {
                    if (cat.getAvailableSeats() < seatsBooked) {
                        throw new IllegalStateException("Not enough seats available");
                    }
                    cat.setAvailableSeats(cat.getAvailableSeats() - seatsBooked);
                });

        eventRepository.save(event);
    }

    private EventResponseDTO toResponseDTO(Event event) {
        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setDate(event.getDate());
        dto.setCategory(event.getCategory());
        dto.setImageUrl(event.getImageUrl());
        dto.setSeatCategories(event.getSeatCategories().stream()
                .map(cat -> {
                    SeatCategoryResponseDTO catDto = new SeatCategoryResponseDTO();
                    catDto.setCategoryName(cat.getCategoryName());
                    catDto.setTotalSeats(cat.getTotalSeats());
                    catDto.setAvailableSeats(cat.getAvailableSeats());
                    catDto.setPricePerSeat(cat.getPricePerSeat());
                    return catDto;
                })
                .collect(Collectors.toList()));
        return dto;
    }

    public void deleteEvent(int id) {
        if (!eventRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }

    public EventResponseDTO updateEvent(int id, EventRequestDTO request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));

        // Update fields
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setDate(request.getDate());
        event.setCategory(request.getCategory());
        event.setImageUrl(request.getImageUrl());
        event.setSeatCategories(request.getSeatCategories().stream()
                .map(cat -> new Event.SeatCategory(
                        cat.getCategoryName(),
                        cat.getTotalSeats(),
                        cat.getPricePerSeat()))
                .collect(Collectors.toList()));

        Event updatedEvent = eventRepository.save(event);
        return toResponseDTO(updatedEvent);
    }

    // In EventService.java - Add this method
    @Transactional
    public void restoreAvailableSeats(int eventId, String categoryName, int seatsToRestore) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        event.getSeatCategories().stream()
                .filter(cat -> cat.getCategoryName().equalsIgnoreCase(categoryName))
                .findFirst()
                .ifPresent(cat -> {
                    cat.setAvailableSeats(cat.getAvailableSeats() + seatsToRestore);
                });

        eventRepository.save(event);
    }

    public List<EventResponseDTO> getEventsByCategory(String categoryName) {
        try {
            EventCategory category = EventCategory.valueOf(categoryName.toUpperCase());
            return eventRepository.findByCategory(category).stream()
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category: " + categoryName);
        }
    }

    public List<EventResponseDTO> searchEvents(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllEvents();
        }

        return eventRepository.searchEvents(searchTerm.trim()).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventResponseDTO> searchEventsByCategory(String searchTerm, String categoryName) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getEventsByCategory(categoryName);
        }

        try {
            EventCategory category = EventCategory.valueOf(categoryName.toUpperCase());
            return eventRepository.searchEventsByCategory(searchTerm.trim(), category).stream()
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category: " + categoryName);
        }
    }

    public List<EventResponseDTO> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now()).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventResponseDTO> searchUpcomingEvents(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getUpcomingEvents();
        }

        return eventRepository.searchUpcomingEvents(searchTerm.trim(), LocalDateTime.now()).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
