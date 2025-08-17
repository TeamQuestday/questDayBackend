package com.project.questday.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomExceptionResponse {
    private final int code;
    private final String errorCode;
    private final String message;
    private final String traceId; // 로그 추적용 ID

    public CustomExceptionResponse(HttpStatusCode status, String errorCode, String message, String traceId) {
        this.code = status.value();
        this.errorCode = errorCode;
        this.message = message;
        this.traceId = traceId;
    }
}
