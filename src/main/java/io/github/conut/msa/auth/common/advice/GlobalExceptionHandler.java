package io.github.conut.msa.auth.common.advice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.conut.msa.auth.common.exception.ServiceUnavailableException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<?> handleServiceUnavailableException(ServiceUnavailableException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(
                Map.of(
                    "status", HttpStatus.SERVICE_UNAVAILABLE.value(),
                    "error", "Service Unavailable",
                    "message", exception.getMessage()
                )
            );
    }
}
