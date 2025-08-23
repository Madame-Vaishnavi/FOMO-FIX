package com.project.eventservice.kafka;

import com.project.eventservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class BookingEventListener {

    @Autowired
    EventService eventService;

    // Update existing BookingEventConsumer.java in Event Service
    @KafkaListener(topics = "booking-created", groupId = "event-service-group")
    public void handleBookingCreated(Map<String, Object> bookingEvent) {
        Integer eventId = (Integer) bookingEvent.get("eventId");
        String categoryName = (String) bookingEvent.get("categoryName");
        Integer seatsBooked = (Integer) bookingEvent.get("seatBooked");

        eventService.decrementAvailableSeats(eventId, categoryName, seatsBooked);
    }


}