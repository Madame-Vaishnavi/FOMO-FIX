package com.project.paymentservice.repository;

import com.project.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBookingId(Integer bookingId);
    List<Payment> findByUserEmail(String userEmail);
    List<Payment> findByEventId(Integer eventId);
    Optional<Payment> findByTransactionId(String transactionId);

}
