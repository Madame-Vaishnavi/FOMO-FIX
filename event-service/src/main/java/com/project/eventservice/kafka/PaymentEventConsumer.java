// Create: PaymentEventConsumer.java in Event Service
package com.project.eventservice.kafka;

import com.project.eventservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class PaymentEventConsumer {

    @Autowired
    private EventService eventService;

    @KafkaListener(topics = "payment-events", groupId = "event-service-group")
    public void handlePaymentEvent(Map<String, Object> paymentEvent) {
        String eventType = (String) paymentEvent.get("eventType");
        Integer eventId = (Integer) paymentEvent.get("eventId");
        String categoryName = (String) paymentEvent.get("categoryName");
        Integer seatsBooked = (Integer) paymentEvent.get("seatsBooked");

        if ("PAYMENT_FAILED".equals(eventType)) {
            // Restore seats if payment failed
            eventService.restoreAvailableSeats(eventId, categoryName, seatsBooked);
        }
        // For PAYMENT_SUCCESS, seats are already decremented, so no action needed
    }
}
