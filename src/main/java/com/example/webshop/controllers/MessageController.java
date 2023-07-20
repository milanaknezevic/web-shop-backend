package com.example.webshop.controllers;

import com.example.webshop.models.dto.Message;
import com.example.webshop.models.requests.MessageRequest;
import com.example.webshop.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message insert(@RequestBody MessageRequest messageRequest, Authentication authentication) {
        return messageService.insert(messageRequest, authentication);
    }
}
