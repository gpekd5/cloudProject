package com.example.cloudproject.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 잘못된 요청 상태 예외
 */
public class BadRequestException extends ServiceException {

    /**
     * 잘못된 요청 예외 생성자
     *
     * @param message 예외 메시지
     */
    public BadRequestException(String message) {

        super(HttpStatus.BAD_REQUEST, message);
    }
}
