package com.project.eventservice.dto;

public class SeatCategoryResponseDTO {
    private String categoryName;
    private int totalSeats;
    private int availableSeats;
    private double pricePerSeat;

    public SeatCategoryResponseDTO() {}

    public SeatCategoryResponseDTO(String categoryName, int totalSeats, int availableSeats, double pricePerSeat) {
        this.categoryName = categoryName;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.pricePerSeat = pricePerSeat;
    }

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
