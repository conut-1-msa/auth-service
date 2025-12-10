package io.github.conut.msa.auth.refreshtoken.advice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.conut.msa.auth.refreshtoken.exception.RefreshTokenMissingException;

@RestControllerAdvice
public class RefreshTokenExceptionHandler {
    @ExceptionHandler(RefreshTokenMissingException.class)
    public ResponseEntity<?> handleRefreshTokenMissingException(RefreshTokenMissingException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                Map.of(
                    "status", HttpStatus.UNAUTHORIZED.value(),
                    "error", "Unauthorized",
                    "message", exception.getMessage()
                )
            );
    }
}
