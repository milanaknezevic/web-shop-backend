package com.example.webshop.services.impl;

import com.example.webshop.models.dto.JwtUser;
import com.example.webshop.models.enums.UserStatus;
import com.example.webshop.repositories.KorisnikRepository;
import com.example.webshop.services.JwtUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {
    private final ModelMapper modelMapper;
    private final KorisnikRepository korisnikRepository;

    public JwtUserDetailsServiceImpl(ModelMapper modelMapper, KorisnikRepository korisnikRepository) {
        this.modelMapper = modelMapper;
        this.korisnikRepository = korisnikRepository;
    }


    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return modelMapper.map(korisnikRepository.findByKorisnickoImeAndStatus(username, UserStatus.ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException(username)), JwtUser.class);
    }

}
