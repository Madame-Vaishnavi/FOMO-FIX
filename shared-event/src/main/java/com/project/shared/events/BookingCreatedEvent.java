// BookingCreatedEvent.java
package com.project.shared.events;

public record BookingCreatedEvent(
        int eventId,
        String categoryName,
        int seatsBooked
) {}
