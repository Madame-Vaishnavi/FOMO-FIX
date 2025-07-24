package com.project.notificationservice.kafka;

import com.project.notificationservice.DTO.NotificationEventDTO;
import com.project.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationKafkaConsumer {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "booking-created", groupId = "notification-service-group")
    public void handleBookingEvents(Map<String, Object> event) {
        // Extract fields from the map
        String userEmail = (String) event.get("userEmail");
        String subject = "Booking Confirmation";
        String message = "Your booking is confirmed!"; // You can build a message using event data

        // Call your notification logic here
        NotificationEventDTO dto = new NotificationEventDTO();
        dto.setUserEmail(userEmail);
        dto.setSubject(subject);
        dto.setMessage(message);
        notificationService.sendNotification(dto);
    }

    @KafkaListener(topics = "payment-events", groupId = "notification-service-group")
    public void handlePaymentEvents(Map<String, Object> event) {
        String userEmail = (String) event.get("userEmail");
        String status = (String) event.get("status");
        String subject = "Payment " + status;
        String message = "Your payment status is: " + status;

        NotificationEventDTO dto = new NotificationEventDTO();
        dto.setUserEmail(userEmail);
        dto.setSubject(subject);
        dto.setMessage(message);
        notificationService.sendNotification(dto);
    }
}
