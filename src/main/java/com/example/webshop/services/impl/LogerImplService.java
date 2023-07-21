package com.example.webshop.services.impl;

import com.example.webshop.models.entities.LogerEntity;
import com.example.webshop.repositories.LogerRepository;
import com.example.webshop.services.LogerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogerImplService implements LogerService {
    private final LogerRepository logerRepository;
    public static final String LEVEL_INFO = "INFO";

    @Override
    public void insertLog(String description,String classLog)
    {
        LogerEntity loggerEntity= new LogerEntity();
        loggerEntity.setPoruka(description);
        loggerEntity.setLevel(LEVEL_INFO);
        loggerEntity.setDatum(new Date());
        loggerEntity.setLog(classLog);
        logerRepository.saveAndFlush(loggerEntity);
    }
}
