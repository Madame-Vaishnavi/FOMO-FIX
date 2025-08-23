package com.project.bookingservice.repository;

import com.project.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByUserEmail(String email);

    List<Booking> findByUserId(String userId);

    List<Booking> findByEventId(int eventId);
}
