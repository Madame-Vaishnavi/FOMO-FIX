package com.project.bookingservice.dto;

public class BookingRequestDTO {
    private int eventId;
    private String categoryName;
    private int seatsRequested;
    private String userEmail;
    private String userId;

    public BookingRequestDTO() {
    }

    public BookingRequestDTO(int eventId, String categoryName, int seatsRequested, String userEmail, String userId) {
        this.eventId = eventId;
        this.categoryName = categoryName;
        this.seatsRequested = seatsRequested;
        this.userEmail = userEmail;
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSeatsRequested() {
        return seatsRequested;
    }

    public void setSeatsRequested(int seatsRequested) {
        this.seatsRequested = seatsRequested;
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
}
