package com.example.webshop.exceptions;

import org.springframework.http.HttpStatus;
//409 vec yauyeto korisnicko ime ili mejl prilikom registracije
public class ConflictException extends HttpException {

    public ConflictException() {
        super(HttpStatus.CONFLICT, null);
    }


    public ConflictException(Object data) {
        super(HttpStatus.CONFLICT, data);
    }
}