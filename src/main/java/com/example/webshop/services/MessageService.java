package com.example.webshop.services;

import com.example.webshop.exceptions.NotFoundException;
import com.example.webshop.models.dto.Message;
import com.example.webshop.models.requests.MessageRequest;
import org.springframework.security.core.Authentication;

public interface MessageService {
    Message insert(MessageRequest ocjenaRequest, Authentication authentication) throws NotFoundException;

}
