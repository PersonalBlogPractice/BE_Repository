package io.github.tato126.practice.common.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String message,
        int status,
        String code,
        LocalDateTime timestamp,
        List<FieldError> errors
) {

    public static ErrorResponse of(String message, int status, String code) {
        return new ErrorResponse(
                message,
                status,
                code,
                LocalDateTime.now(),
                null);
    }

    public static ErrorResponse of(String message, int status, String code, List<FieldError> errors) {
        return new ErrorResponse(message,
                status,
                code,
                LocalDateTime.now(),
                errors);
    }

    public record FieldError(
            String field,
            String rejectedValue,
            String message
    ) {
    }

}
