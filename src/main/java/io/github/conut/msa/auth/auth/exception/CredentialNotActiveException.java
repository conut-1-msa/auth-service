package io.github.conut.msa.auth.auth.exception;

public class CredentialNotActiveException extends RuntimeException{
    public CredentialNotActiveException() {
        super("Credential is not active");
    }
}
