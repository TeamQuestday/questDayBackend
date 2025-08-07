package com.project.questday.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String REQUEST_ID = "requestId";
    private static final String USER_ID = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 요청 ID 설정
        String requestId = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, requestId);

        // 유저 ID 설정 (예: SecurityContext에서 추출)
        String userId = extractUserIdFromRequest(request); // 구현 필요
        MDC.put(USER_ID, userId != null ? userId : "anonymous");

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.clear(); // 꼭 해줘야 memory leak 방지
    }

    private String extractUserIdFromRequest(HttpServletRequest request) {
        // 예시: 헤더에서 가져오기
        return request.getHeader("X-USER-ID");
        // 또는 SecurityContextHolder.getContext().getAuthentication() 등
    }
}
