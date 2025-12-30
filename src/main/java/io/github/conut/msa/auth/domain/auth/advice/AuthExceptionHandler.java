package io.github.conut.msa.auth.domain.auth.advice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.conut.msa.auth.domain.auth.exception.CredentialNotActiveException;
import io.github.conut.msa.auth.domain.auth.exception.InvalidCredentialException;
import io.github.conut.msa.auth.domain.auth.exception.InviteCodeRequiredException;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<?> handleInvalidCredentialException(InvalidCredentialException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                Map.of(
                    "status", HttpStatus.UNAUTHORIZED.value(),
                    "error", "Unauthorized",
                    "message", exception.getMessage()
                )
            );
    }

    @ExceptionHandler(CredentialNotActiveException.class)
    public ResponseEntity<?> handleCredentialNotActiveException(CredentialNotActiveException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                Map.of(
                    "status", HttpStatus.UNAUTHORIZED.value(),
                    "error", "Unauthorized",
                    "message", "Unauthorized"
                )
            );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                Map.of(
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Internal Server Error",
                    "message", "Internal Server Error"
                )
            );
    }

    @ExceptionHandler(InviteCodeRequiredException.class)
    public ResponseEntity<?> handleInviteCodeRequiredException(InviteCodeRequiredException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(
                Map.of(
                    "status", HttpStatus.FORBIDDEN.value(),
                    "error", "Forbidden",
                    "message", exception.getMessage()
                )
            );
    }
}
