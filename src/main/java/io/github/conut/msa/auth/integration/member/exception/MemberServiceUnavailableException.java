package io.github.conut.msa.auth.integration.member.exception;

import io.github.conut.msa.auth.common.exception.ServiceUnavailableException;

public class MemberServiceUnavailableException extends ServiceUnavailableException {
    public MemberServiceUnavailableException(Throwable cause) {
        super("Member service is unavailable", cause);
    }
}
