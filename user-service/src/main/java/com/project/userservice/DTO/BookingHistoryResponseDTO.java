package com.project.userservice.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class BookingHistoryResponseDTO {
    private List<BookingWithPaymentDTO> bookings;

    public BookingHistoryResponseDTO() {
    }

    public BookingHistoryResponseDTO(List<BookingWithPaymentDTO> bookings) {
        this.bookings = bookings;
    }

    public List<BookingWithPaymentDTO> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingWithPaymentDTO> bookings) {
        this.bookings = bookings;
    }

    public static class BookingWithPaymentDTO {
        private int bookingId;
        private int eventId;
        private String eventName;
        private String categoryName;
        private int seatBooked;
        private double price;
        private LocalDateTime bookingTime;
        private String status;
        private PaymentInfoDTO paymentInfo;

        public BookingWithPaymentDTO() {
        }

        public BookingWithPaymentDTO(int bookingId, int eventId, String eventName, String categoryName,
                int seatBooked, double price, LocalDateTime bookingTime, String status,
                PaymentInfoDTO paymentInfo) {
            this.bookingId = bookingId;
            this.eventId = eventId;
            this.eventName = eventName;
            this.categoryName = categoryName;
            this.seatBooked = seatBooked;
            this.price = price;
            this.bookingTime = bookingTime;
            this.status = status;
            this.paymentInfo = paymentInfo;
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

        public PaymentInfoDTO getPaymentInfo() {
            return paymentInfo;
        }

        public void setPaymentInfo(PaymentInfoDTO paymentInfo) {
            this.paymentInfo = paymentInfo;
        }
    }

    public static class PaymentInfoDTO {
        private int paymentId;
        private Double amount;
        private String paymentMode;
        private String status;
        private String transactionId;
        private LocalDateTime paymentDate;

        public PaymentInfoDTO() {
        }

        public PaymentInfoDTO(int paymentId, Double amount, String paymentMode, String status,
                String transactionId, LocalDateTime paymentDate) {
            this.paymentId = paymentId;
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
}

