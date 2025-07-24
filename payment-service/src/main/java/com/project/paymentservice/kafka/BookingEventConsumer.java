package com.project.paymentservice.kafka;

import com.project.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BookingEventConsumer {

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "booking-created", groupId = "payment-service-group")
    public void handleBookingCreated(Map<String, Object> bookingEvent) {
        Integer bookingId = (Integer) bookingEvent.get("bookingId");
        Integer eventId = (Integer) bookingEvent.get("eventId");
        String userEmail = (String) bookingEvent.get("userEmail");
        Double totalAmount = (Double) bookingEvent.get("totalAmount");

        System.out.println("Processing booking event for booking ID: " + bookingId); // Add logging

        paymentService.handleBookingCreated(bookingId, eventId, userEmail, totalAmount);
    }

}
