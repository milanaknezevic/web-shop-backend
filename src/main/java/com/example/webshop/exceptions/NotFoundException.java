package com.example.webshop.exceptions;
//404
import org.springframework.http.HttpStatus;

import java.net.HttpRetryException;

public class NotFoundException extends HttpException {
    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, null);
    }

    public NotFoundException(Object data) {
        super(HttpStatus.NOT_FOUND, data);
    }
}