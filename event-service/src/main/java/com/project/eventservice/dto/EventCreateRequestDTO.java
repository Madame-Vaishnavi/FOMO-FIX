package com.project.eventservice.dto;

import com.project.eventservice.enums.EventCategory;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class EventCreateRequestDTO {
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
    private EventCategory category;
    private List<SeatCategoryDTO> seatCategories;
    private MultipartFile image;

    public EventCreateRequestDTO() {}

    public EventCreateRequestDTO(String name, String description, String location, LocalDateTime date, EventCategory category, List<SeatCategoryDTO> seatCategories, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.category = category;
        this.seatCategories = seatCategories;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    public List<SeatCategoryDTO> getSeatCategories() {
        return seatCategories;
    }

    public void setSeatCategories(List<SeatCategoryDTO> seatCategories) {
        this.seatCategories = seatCategories;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    // Convert to EventRequestDTO
    public EventRequestDTO toEventRequestDTO(String imageUrl) {
        return new EventRequestDTO(name, description, location, date, category, seatCategories, imageUrl);
    }
}
