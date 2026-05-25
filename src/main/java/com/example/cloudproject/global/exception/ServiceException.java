package com.example.cloudproject.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 서비스 계층 공통 런타임 예외
 */
@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus status;

    /**
     * HTTP 상태 코드 기반 서비스 예외 생성자
     *
     * @param status HTTP 상태 코드
     * @param message 예외 메시지
     */
    public ServiceException(HttpStatus status, String message)
    {
        super(message);
        this.status = status;
    }
}
