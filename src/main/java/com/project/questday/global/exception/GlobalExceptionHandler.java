package com.project.questday.global.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Valid 유효성 검증 실패 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.warn("[Validation Error] {}", errorMessage, ex);
        return new ResponseEntity<>(
                new CustomExceptionResponse(httpStatus, "VALIDATION_ERROR", errorMessage, getTraceId()),
                httpStatus
        );
    }

    /**
     * BindingException 처리 (form-data 등에서 발생)
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<CustomExceptionResponse> handleBindException(BindException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();

        log.warn("[Bind Error] {}", errorMessage, ex);
        return new ResponseEntity<>(
                new CustomExceptionResponse(httpStatus, "BIND_ERROR", errorMessage, getTraceId()),
                httpStatus
        );
    }

    /**
     * Custom 예외 처리
     */
    @ExceptionHandler(CustomApplicationException.class)
    public ResponseEntity<CustomExceptionResponse> handleCustomApplicationException(CustomApplicationException ex) {
        HttpStatus httpStatus = ex.getErrorCode().getHttpStatus();
        CustomExceptionResponse errorResponse = new CustomExceptionResponse(
                httpStatus,
                ex.getErrorCode().getErrorCode(),
                ex.getErrorCode().getMessage(),
                getTraceId()
        );

        // 사용자 입력 오류는 warn, 서버 오류는 error
        if (httpStatus.is4xxClientError()) {
            log.warn("[CustomException] {} - {}", ex.getErrorCode().getErrorCode(), ex.getMessage(), ex);
        } else {
            log.error("[CustomException] {} - {}", ex.getErrorCode().getErrorCode(), ex.getMessage(), ex);
        }

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * JSON 파싱 에러 처리
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        String errorMessage = "잘못된 JSON 형식입니다.";

        if (cause instanceof InvalidFormatException invalidFormatException) {
            if (!invalidFormatException.getPath().isEmpty()) {
                String fieldName = invalidFormatException.getPath().get(0).getFieldName();
                String targetType = invalidFormatException.getTargetType().getSimpleName();
                errorMessage = String.format(ErrorCode.INVALID_JSON_INPUT.getMessage(), fieldName, targetType);
            }
        }

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        log.warn("[JSON Parse Error] {}", errorMessage, ex);

        return new ResponseEntity<>(
                new CustomExceptionResponse(httpStatus, ErrorCode.INVALID_JSON_INPUT.getErrorCode(), errorMessage, getTraceId()),
                httpStatus
        );
    }

    /**
     * 잘못된 API Endpoint 요청 처리
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CustomExceptionResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        CustomExceptionResponse errorResult = new CustomExceptionResponse(
                httpStatus,
                "ENDPOINT_ERROR",
                "잘못된 요청입니다.",
                getTraceId()
        );

        log.warn("[NoResourceFound] {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorResult, httpStatus);
    }

    /**
     * 최종 예외 처리 (500 Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionResponse> handleException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        CustomExceptionResponse errorResponse = new CustomExceptionResponse(
                httpStatus,
                "SERVER_ERROR",
                "서버 내부 오류가 발생했습니다.",
                getTraceId()
        );

        log.error("[Server Error] {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * 로그 추적 ID 반환 (없으면 null)
     */
    private String getTraceId() {
        return MDC.get("traceId");
    }
}
