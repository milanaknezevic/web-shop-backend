package com.example.webshop;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebShopApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper()
    {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        return mapper;
    }

}
