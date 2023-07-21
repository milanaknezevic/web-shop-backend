package com.example.webshop.services.impl;

import com.example.webshop.exceptions.NotFoundException;
import com.example.webshop.models.dto.JwtUser;
import com.example.webshop.models.dto.Message;
import com.example.webshop.models.entities.KorisnikEntity;
import com.example.webshop.models.entities.PorukaEntity;
import com.example.webshop.models.requests.MessageRequest;
import com.example.webshop.repositories.MessageRepository;
import com.example.webshop.repositories.UserRepository;
import com.example.webshop.services.LogerService;
import com.example.webshop.services.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MessageImplService implements MessageService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final LogerService logerService;

    public MessageImplService(ModelMapper modelMapper, UserRepository userRepository, MessageRepository messageRepository, LogerService logerService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.logerService = logerService;
    }


    @Override
    public Message insert(MessageRequest messageRequest, Authentication authentication) throws NotFoundException {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        KorisnikEntity korisnikEntity = userRepository.findById(user.getId()).orElseThrow(NotFoundException::new);
        PorukaEntity porukaEntity = modelMapper.map(messageRequest, PorukaEntity.class);
        porukaEntity.setId(null);
        porukaEntity.setKorisnik(korisnikEntity);
        porukaEntity.setProcitana(false);
        logerService.insertLog("User: " + user.getUsername() + " has sent message to customer support.",this.getClass().getName());

        return modelMapper.map(messageRepository.saveAndFlush(porukaEntity), Message.class);

    }
}
