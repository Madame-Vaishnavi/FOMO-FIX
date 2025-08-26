package com.project.userservice.DTO;

public class UsernameUpdateDTO {
    private String newUsername;

    public UsernameUpdateDTO() {
    }

    public UsernameUpdateDTO(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }
}

