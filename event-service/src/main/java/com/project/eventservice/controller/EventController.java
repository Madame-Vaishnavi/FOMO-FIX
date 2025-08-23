package com.project.eventservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.eventservice.dto.EventCreateRequestDTO;
import com.project.eventservice.dto.EventRequestDTO;
import com.project.eventservice.dto.EventResponseDTO;
import com.project.eventservice.dto.SeatCategoryDTO;
import com.project.eventservice.enums.EventCategory;
import com.project.eventservice.service.EventService;
import com.project.eventservice.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO request) {
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    @PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponseDTO> createEventWithImage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("date") String date,
            @RequestParam("category") String category,
            @RequestParam("seatCategories") String seatCategoriesJson,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            // Parse the JSON string to List<SeatCategoryDTO>
            ObjectMapper objectMapper = new ObjectMapper();
            List<SeatCategoryDTO> seatCategories = objectMapper.readValue(
                    seatCategoriesJson,
                    new TypeReference<List<SeatCategoryDTO>>() {
                    });

            // Parse date string to LocalDateTime
            LocalDateTime eventDate = LocalDateTime.parse(date);

            // Parse category string to EventCategory enum
            EventCategory eventCategory = EventCategory.valueOf(category.toUpperCase());

            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                imageUrl = imageUploadService.uploadImage(image);
            }

            EventRequestDTO eventRequest = new EventRequestDTO(
                    name, description, location, eventDate, eventCategory, seatCategories, imageUrl);
            return ResponseEntity.ok(eventService.createEvent(eventRequest));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable int id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable int id,
            @RequestBody EventRequestDTO request) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventResponseDTO>> getEventsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(eventService.getEventsByCategory(category));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getEventCategories() {
        List<String> categories = Stream.of(EventCategory.values())
                .map(EventCategory::getDisplayName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            Path filePath = imageUploadService.getImagePath(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
