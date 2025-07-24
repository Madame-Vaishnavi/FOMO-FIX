package com.project.eventservice.dto;

public class SeatCategoryDTO {
    private String categoryName;
    private int totalSeats;
    private double pricePerSeat;

    public SeatCategoryDTO() {}

    public SeatCategoryDTO(String categoryName, int totalSeats, double pricePerSeat) {
        this.categoryName = categoryName;
        this.totalSeats = totalSeats;
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

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }
}
