package com.project.eventservice.dto;

import com.project.eventservice.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public class EventRequestDTO {
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
    private List<SeatCategoryDTO> seatCategories;

    public EventRequestDTO() {}

    public EventRequestDTO(String name, String description, String location, LocalDateTime date, List<SeatCategoryDTO> seatCategories) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.seatCategories = seatCategories;
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

    public List<SeatCategoryDTO> getSeatCategories() {
        return seatCategories;
    }

    public void setSeatCategories(List<SeatCategoryDTO> seatCategories) {
        this.seatCategories = seatCategories;
    }
}
