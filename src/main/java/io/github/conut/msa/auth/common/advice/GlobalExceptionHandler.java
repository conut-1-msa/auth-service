package io.github.conut.msa.auth.common.advice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.conut.msa.auth.integration.member.exception.MemberServiceUnavailableException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MemberServiceUnavailableException.class)
    public ResponseEntity<?> handleMemberServiceUnavailableException(MemberServiceUnavailableException exception) {
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
