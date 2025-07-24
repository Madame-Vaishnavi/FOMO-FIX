package com.project.bookingservice;

import com.project.bookingservice.dto.EventResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="event-service")
public interface EventServiceClient {
    @GetMapping("/api/events/{id}")
    EventResponseDTO getEventById(@PathVariable("id") int id);
}
