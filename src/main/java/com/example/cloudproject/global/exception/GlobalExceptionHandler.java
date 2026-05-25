package com.example.cloudproject.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 애플리케이션 전역 예외 응답 처리기
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 서비스 계층 예외의 HTTP 응답 변환
     *
     * @param ex 서비스 계층 예외
     * @return 예외 상태 코드와 메시지 응답
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException ex) {

        if (ex.getStatus().is5xxServerError()) {
            log.error("[API - ERROR] ServiceException", ex);
        } else {
            log.warn("[API - WARN] ServiceException: {}", ex.getMessage());
        }
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }

    /**
     * 요청 검증 실패 응답 변환
     *
     * @param ex 요청 검증 실패 예외
     * @return 검증 실패 메시지 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("입력 값이 올바르지 않습니다.");

        log.warn("[API - WARN] ValidationException: {}", errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    /**
     * 예상하지 못한 예외의 공통 응답 변환
     *
     * @param ex 예상하지 못한 예외
     * @return 서버 오류 메시지 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {

        log.error("[API - ERROR] UnexpectedException", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 내부 오류가 발생했습니다.");
    }



}
