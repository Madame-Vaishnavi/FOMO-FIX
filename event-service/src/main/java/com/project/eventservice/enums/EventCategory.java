package com.project.eventservice.enums;

public enum EventCategory {
    CONCERT("Concert"),
    THEATER("Theater"),
    SPORTS("Sports"),
    COMEDY("Comedy"),
    OTHER("Other");

    private final String displayName;

    EventCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
