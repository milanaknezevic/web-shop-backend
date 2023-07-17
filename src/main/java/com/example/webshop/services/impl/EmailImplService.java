package com.example.webshop.services.impl;

import com.example.webshop.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailImplService implements EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailImplService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject("WebShopIP - Verification");
        String text = "Welcome to WebShopIP application \n\nACTIVATION CODE: " + code;
        message.setText(text);
        javaMailSender.send(message);
    }
}
