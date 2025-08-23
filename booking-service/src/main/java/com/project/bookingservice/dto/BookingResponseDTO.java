package com.project.bookingservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingResponseDTO {
    private int bookingId;
    private int eventId;
    private String eventName;
    private String categoryName;
    private int seatBooked;
    private double price;
    private String userEmail;
    private String userId;
    private LocalDateTime bookingTime;
    private String status;

    public BookingResponseDTO() {
    }

    public BookingResponseDTO(int bookingId, int eventId, String eventName, String categoryName, int seatBooked,
            double price, String userEmail, String userId, LocalDateTime bookingTime, String status) {
        this.bookingId = bookingId;
        this.eventId = eventId;
        this.eventName = eventName;
        this.categoryName = categoryName;
        this.seatBooked = seatBooked;
        this.price = price;
        this.userEmail = userEmail;
        this.userId = userId;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
