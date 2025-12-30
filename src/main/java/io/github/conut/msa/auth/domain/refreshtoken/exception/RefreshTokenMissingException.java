package io.github.conut.msa.auth.domain.refreshtoken.exception;

public class RefreshTokenMissingException extends RuntimeException {
    public RefreshTokenMissingException() {
        super("Refresh token is missing");
    }
}
