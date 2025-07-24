package com.project.eventservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EventResponseDTO {
    private int id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
    private List<SeatCategoryResponseDTO> seatCategories;

    public EventResponseDTO() {}

    public EventResponseDTO(int id, String name, String description, String location, LocalDateTime date, List<SeatCategoryResponseDTO> seatCategories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.seatCategories = seatCategories;
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

    public List<SeatCategoryResponseDTO> getSeatCategories() {
        return seatCategories;
    }

    public void setSeatCategories(List<SeatCategoryResponseDTO> seatCategories) {
        this.seatCategories = seatCategories;
    }
}
