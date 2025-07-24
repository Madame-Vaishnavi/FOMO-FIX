package com.project.bookingservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EventResponseDTO {
    private int id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
    private List<SeatCategoryDTO> seatCategory;

    public EventResponseDTO() {}

    public EventResponseDTO(int id, String name, String description, String location, LocalDateTime date, List<SeatCategoryDTO> seatCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.seatCategory = seatCategory;
    }

    public static class SeatCategoryDTO {
        private String categoryName;
        private int totalSeats;
        private int availableSeats;
        private double pricePerSeat;

        public String getCategoryName() {
            return categoryName;
        }

        public int getTotalSeats() {
            return totalSeats;
        }

        public int getAvailableSeats() {
            return availableSeats;
        }

        public double getPricePerSeat() {
            return pricePerSeat;
        }
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

    public List<SeatCategoryDTO> getSeatCategory() {
        return seatCategory;
    }

    public void setSeatCategories(List<SeatCategoryDTO> seatCategories) {
        this.seatCategory = seatCategories;
    }
}
