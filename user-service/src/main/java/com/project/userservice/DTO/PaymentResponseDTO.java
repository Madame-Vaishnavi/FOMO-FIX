package com.project.userservice.DTO;

import java.time.LocalDateTime;

public class PaymentResponseDTO {
    private int paymentId;
    private Integer bookingId;
    private Integer eventId;
    private String userEmail;
    private Double amount;
    private String paymentMode;
    private String status;
    private String transactionId;
    private LocalDateTime paymentDate;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(int paymentId, Integer bookingId, Integer eventId, String userEmail,
            Double amount, String paymentMode, String status, String transactionId,
            LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.eventId = eventId;
        this.userEmail = userEmail;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.status = status;
        this.transactionId = transactionId;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}

