package com.example.cloudproject.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 리소스 미존재 상태 예외
 */
public class NotFoundException extends ServiceException {

    /**
     * 리소스 미존재 예외 생성자
     *
     * @param message 예외 메시지
     */
    public NotFoundException(String message) {

        super(HttpStatus.NOT_FOUND, message);
    }
}
