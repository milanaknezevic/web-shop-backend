package com.example.webshop.exceptions;

import org.springframework.http.HttpStatus;
//401 loginujes se a nemas pravo da se loginujes
public class UnauthorizedException extends HttpException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, null);
    }

    public UnauthorizedException(Object data) {
        super(HttpStatus.UNAUTHORIZED, data);
    }
}