package com.project.eventservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String location;

    @Column(nullable = false)
    private LocalDateTime date;

    @ElementCollection
    @CollectionTable(
            name = "seat_categories",
            joinColumns = @JoinColumn(name = "event_id")
    )
    private List<SeatCategory> seatCategories = new ArrayList<>();

    // Constructors
    public Event() {}

    public Event(int id, String name, String description, String location, LocalDateTime date, List<SeatCategory> seatCategories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.seatCategories = seatCategories;
    }

    // Getters and Setters

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

    public List<SeatCategory> getSeatCategories() {
        return seatCategories;
    }

    public void setSeatCategories(List<SeatCategory> seatCategories) {
        this.seatCategories = seatCategories;
    }

    // Embeddable class for seat categories
    @Embeddable
    public static class SeatCategory {

        private String categoryName;
        private int totalSeats;
        private int availableSeats;
        private double pricePerSeat;

        // Constructors
        public SeatCategory() {}

        public SeatCategory(String categoryName, int totalSeats, double pricePerSeat) {
            this.categoryName = categoryName;
            this.totalSeats = totalSeats;
            this.availableSeats = totalSeats; // Initialize available seats as total seats
            this.pricePerSeat = pricePerSeat;
        }

        // Getters and Setters

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getTotalSeats() {
            return totalSeats;
        }

        public void setTotalSeats(int totalSeats) {
            this.totalSeats = totalSeats;
        }

        public int getAvailableSeats() {
            return availableSeats;
        }

        public void setAvailableSeats(int availableSeats) {
            this.availableSeats = availableSeats;
        }

        public double getPricePerSeat() {
            return pricePerSeat;
        }

        public void setPricePerSeat(double pricePerSeat) {
            this.pricePerSeat = pricePerSeat;
        }
    }
}