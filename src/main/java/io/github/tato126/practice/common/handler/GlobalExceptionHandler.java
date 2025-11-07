package io.github.tato126.practice.common.handler;

import io.github.tato126.practice.common.dto.ErrorResponse;
import io.github.tato126.practice.common.excetion.PostAccessDeniedException;
import io.github.tato126.practice.common.excetion.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 포스트를 찾을 수 없음
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ErrorResponse handlePostNotFoundException(PostNotFoundException e) {
        log.error("PostNotFoundException: {}", e.getMessage());
        return ErrorResponse.of(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                "POST_NOT_FOUND"
        );
    }

    // 2. 포스트 접근 권한 없음
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PostAccessDeniedException.class)
    public ErrorResponse handlePostAccessDeniedException(PostAccessDeniedException e) {
        log.error("PostAccessDeniedException: {}", e.getMessage());
        return ErrorResponse.of(
                e.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                "POST_ACCESS_DENIED"
        );
    }

    // 3. 입력 검증 실패 (@Valid)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage());

        BindingResult bindingResult = e.getBindingResult();
        List<ErrorResponse.FieldError> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse.FieldError(
                        error.getField(),
                        error.getRejectedValue() != null ? error.getRejectedValue().toString() : "",
                        error.getDefaultMessage()
                ))
                .toList();

        return ErrorResponse.of(
                "입력값 검증에 실패했습니다.",
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_FAILED",
                fieldErrors
        );
    }

    // 4. 일반적인 예외 (fallback)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return ErrorResponse.of(
                "서버 내부 오류가 발생했습니다.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR"
        );
    }
}
