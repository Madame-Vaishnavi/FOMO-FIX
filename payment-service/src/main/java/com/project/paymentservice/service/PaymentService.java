package com.project.paymentservice.service;

import com.project.paymentservice.DTO.PaymentRequestDTO;
import com.project.paymentservice.DTO.PaymentResponseDTO;
import com.project.paymentservice.enums.PaymentStatus;
import com.project.paymentservice.exception.PaymentException;
import com.project.paymentservice.model.Payment;
import com.project.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequest) {
        Payment payment = paymentRepository.findByBookingId(paymentRequest.getBookingId())
                .orElseThrow(() -> new PaymentException("Payment record not found for booking ID: " + paymentRequest.getBookingId()));

        boolean paymentSuccess = simulatePaymentGateway(paymentRequest);

        if (paymentSuccess) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaymentMode(paymentRequest.getPaymentMode());
            payment.setTransactionId(UUID.randomUUID().toString());
            payment.setPaymentDate(LocalDateTime.now());
            payment.setGatewayResponse("Payment processed successfully");
            publishPaymentEvent(payment, "PAYMENT_SUCCESS");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setGatewayResponse("Payment failed - insufficient funds");
            publishPaymentEvent(payment, "PAYMENT_FAILED");
        }

        payment.setUpdatedAt(LocalDateTime.now());
        return toResponseDTO(paymentRepository.save(payment));
    }

    public void handleBookingCreated(Integer bookingId, Integer eventId, String userEmail, Double amount) {
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setEventId(eventId);
        payment.setUserEmail(userEmail);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);
    }

    public PaymentResponseDTO getPaymentByBookingId(Integer bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new PaymentException("Payment not found for booking ID: " + bookingId));
    }

    public List<PaymentResponseDTO> getPaymentsByUser(String userEmail) {
        return paymentRepository.findByUserEmail(userEmail).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PaymentResponseDTO getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new PaymentException("Payment not found with ID: " + paymentId));
    }

    private boolean simulatePaymentGateway(PaymentRequestDTO request) {
        return !request.getCardNumber().endsWith("0000");
    }

    private void publishPaymentEvent(Payment payment, String eventType) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType", eventType);
        event.put("paymentId", payment.getPaymentId());
        event.put("bookingId", payment.getBookingId());
        event.put("eventId", payment.getEventId());
        event.put("userEmail", payment.getUserEmail());
        event.put("amount", payment.getAmount());
        event.put("status", payment.getStatus().toString());
        event.put("transactionId", payment.getTransactionId());
        kafkaTemplate.send("payment-events", event);
    }

    private PaymentResponseDTO toResponseDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBookingId());
        dto.setEventId(payment.getEventId());
        dto.setUserEmail(payment.getUserEmail());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMode(payment.getPaymentMode());
        dto.setStatus(payment.getStatus());
        dto.setTransactionId(payment.getTransactionId());
        dto.setPaymentDate(payment.getPaymentDate());
        return dto;
    }
}