package io.github.conut.msa.auth.domain.auth.exception;

public class CredentialNotActiveException extends RuntimeException{
    public CredentialNotActiveException() {
        super("Credential is not active");
    }
}
