package com.project.paymentservice.DTO;

public class BookingEventDTO {

    private Integer bookingId;
    private Integer eventId;
    private String userEmail;
    private Double totalAmount;
    private String categoryName;
    private Integer seatBooked;

    public BookingEventDTO() {}

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getSeatBooked() {
        return seatBooked;
    }

    public void setSeatBooked(Integer seatBooked) {
        this.seatBooked = seatBooked;
    }
}
