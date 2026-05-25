package com.example.cloudproject.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 서버 내부 오류 상태 예외
 */
public class InternalServerException extends ServiceException {

    /**
     * 서버 내부 오류 예외 생성자
     *
     * @param message 예외 메시지
     */
    public InternalServerException(String message) {

        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
