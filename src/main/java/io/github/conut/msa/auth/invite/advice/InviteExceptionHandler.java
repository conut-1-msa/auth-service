package io.github.conut.msa.auth.invite.advice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.conut.msa.auth.invite.exception.InviteCodeNotFoundException;

@RestControllerAdvice
public class InviteExceptionHandler {
    @ExceptionHandler(InviteCodeNotFoundException.class)
    public ResponseEntity<?> handleInviteCodeNotFoundException(InviteCodeNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "error", "Bad Request",
                    "message", "Invalid invite code"
                )
            );
    }
}
