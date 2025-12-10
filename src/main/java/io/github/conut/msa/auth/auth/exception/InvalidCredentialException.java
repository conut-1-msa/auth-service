package io.github.conut.msa.auth.auth.exception;

public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException() {
        super("Invalid userid or password");
    }
}
