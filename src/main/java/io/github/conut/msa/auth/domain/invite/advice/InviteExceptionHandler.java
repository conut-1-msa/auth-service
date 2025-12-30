package io.github.conut.msa.auth.domain.invite.advice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.conut.msa.auth.domain.invite.exception.InviteCodeNotFoundException;
import io.github.conut.msa.auth.domain.invite.exception.NoInviteCodeConsumedException;

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

    @ExceptionHandler(NoInviteCodeConsumedException.class)
    public ResponseEntity<?> handleNoInviteCodeConsumedException(NoInviteCodeConsumedException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(
                Map.of(
                    "status", HttpStatus.CONFLICT.value(),
                    "error", "Conflict",
                    "message", "Invite code consumed"
                )
            );
    }
}
