package io.github.conut.msa.auth.member.exception;

public class MemberServiceUnavailableException extends RuntimeException {
    public MemberServiceUnavailableException(Throwable cause) {
        super("Member service is unavailable", cause);
    }
}
