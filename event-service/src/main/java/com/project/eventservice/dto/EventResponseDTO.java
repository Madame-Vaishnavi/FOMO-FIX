package com.project.eventservice.dto;

import com.project.eventservice.enums.EventCategory;

import java.time.LocalDateTime;
import java.util.List;

public class EventResponseDTO {
    private int id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
    private EventCategory category;
    private List<SeatCategoryResponseDTO> seatCategories;
    private String imageUrl;

    public EventResponseDTO() {}

    public EventResponseDTO(int id, String name, String description, String location, LocalDateTime date,EventCategory category, List<SeatCategoryResponseDTO> seatCategories, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.category = category;
        this.seatCategories = seatCategories;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<SeatCategoryResponseDTO> getSeatCategories() {
        return seatCategories;
    }

    public void setSeatCategories(List<SeatCategoryResponseDTO> seatCategories) {
        this.seatCategories = seatCategories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
