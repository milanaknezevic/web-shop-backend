package com.example.webshop.services.impl;

import com.example.webshop.services.EmailService;
import com.example.webshop.services.LogerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@RequiredArgsConstructor
public class EmailImplService implements EmailService {
    private final JavaMailSender javaMailSender;
    private final LogerService logerService;

    @Value("${spring.mail.username}")
    private String sender;


    @Override
    public void sendEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject("WebShopIP - Verification");
        String text = "Welcome to WebShopIP application \n\nACTIVATION CODE: " + code;
        message.setText(text);
        javaMailSender.send(message);
        logerService.insertLog(" The mail has sent to: " + to,this.getClass().getName());

    }
}
