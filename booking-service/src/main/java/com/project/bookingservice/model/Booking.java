package com.project.bookingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int eventId;
    private String eventName;
    private String categoryName;
    private int seatBooked;
    private double price;
    private String userEmail;
    private LocalDateTime bookingtime;
    private String status;

    public Booking() {}

    public Booking(int id, int eventId, String eventName, String categoryName, int seatBooked, double price, String userEmail, LocalDateTime bookingtime, String status) {
        this.id = id;
        this.eventId = eventId;
        this.eventName = eventName;
        this.categoryName = categoryName;
        this.seatBooked = seatBooked;
        this.price = price;
        this.userEmail = userEmail;
        this.bookingtime = bookingtime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSeatBooked() {
        return seatBooked;
    }

    public void setSeatBooked(int seatBooked) {
        this.seatBooked = seatBooked;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getBookingtime() {
        return bookingtime;
    }

    public void setBookingtime(LocalDateTime bookingtime) {
        this.bookingtime = bookingtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
