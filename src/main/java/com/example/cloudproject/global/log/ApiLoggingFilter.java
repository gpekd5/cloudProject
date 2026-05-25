package com.example.cloudproject.global.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * API 요청/응답 로그와 처리 시간 기록 필터
 */
@Slf4j
@Component
public class ApiLoggingFilter extends OncePerRequestFilter {

    private static final String ACTUATOR_PATH = "/actuator";

    /**
     * Actuator 경로 로깅 제외 여부
     *
     * @param request HTTP 요청
     * @return 필터 제외 여부
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith(ACTUATOR_PATH);
    }

    /**
     * API 요청/응답 로그 및 처리 시간 측정
     *
     * @param request HTTP 요청
     * @param response HTTP 응답
     * @param filterChain 다음 필터 체인
     * @throws ServletException 서블릿 처리 예외
     * @throws IOException 입출력 예외
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 요청 진입 로그
        log.info("[API REQUEST] {} {}", method, uri);

        try {
            filterChain.doFilter(request, response);
        } finally {
            long elapsedTime = System.currentTimeMillis() - startTime;

            // 응답 반환 로그
            log.info(
                    "[API RESPONSE] {} {} status={} time={}ms",
                    method,
                    uri,
                    response.getStatus(),
                    elapsedTime
            );
        }
    }
}