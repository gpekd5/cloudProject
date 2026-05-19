package com.example.cloudproject.global.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends ServiceException {

    public InternalServerException(String message) {

        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
