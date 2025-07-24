package com.project.notificationservice.service;

import com.project.notificationservice.DTO.NotificationEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendNotification(NotificationEventDTO event) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(event.getUserEmail());
            mailMessage.setSubject(event.getSubject());
            mailMessage.setText((event.getMessage()));
            mailSender.send(mailMessage);

            System.out.println("Notification sent to " + event.getUserEmail());
        }
        catch(Exception e){
            System.out.println("Failed to send notification: " + e.getMessage());
        }
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vaishnavi.singh.260104@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
