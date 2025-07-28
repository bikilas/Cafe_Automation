// src/main/java/com/kifiya/service/EmailService.java (conceptual)
package com.kifiya.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

   // You'll need to configure JavaMailSender in your application.properties
   // and implement the actual email sending logic here.
   // private final JavaMailSender mailSender;

   public void sendPasswordResetEmail(String toEmail, String resetToken) {
       // Construct the reset link: e.g., "http://localhost:8080/reset-password?token=" + resetToken
       String resetLink = "http://localhost:3000/reset-password?token=" + resetToken; // Assuming frontend handles this

       // Example email content (customize)
       String subject = "Password Reset Request";
       String body = "To reset your password, please click on the following link: " + resetLink
               + "\n\nThis link will expire in " + 60 + " minutes."; // Use expirationMinutes from config

       System.out.println("Sending email to: " + toEmail);
       System.out.println("Subject: " + subject);
       System.out.println("Body: " + body);

       // Here you would use JavaMailSender to actually send the email
       // SimpleMailMessage message = new SimpleMailMessage();
       // message.setFrom("noreply@yourdomain.com");
       // message.setTo(toEmail);
       // message.setSubject(subject);
       // message.setText(body);
       // mailSender.send(message);
   }
}