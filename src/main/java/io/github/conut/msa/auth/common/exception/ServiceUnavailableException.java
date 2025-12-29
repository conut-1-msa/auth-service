package io.github.conut.msa.auth.common.exception;

public class ServiceUnavailableException extends RuntimeException{
    public ServiceUnavailableException(Throwable cause) {
        super("Service is unavailable", cause);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
