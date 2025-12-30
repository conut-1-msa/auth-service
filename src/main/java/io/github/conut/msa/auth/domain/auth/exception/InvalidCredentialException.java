package io.github.conut.msa.auth.domain.auth.exception;

public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException() {
        super("Invalid userid or password");
    }
}
