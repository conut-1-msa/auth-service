package io.github.conut.msa.auth.domain.auth.exception;

public class InviteCodeRequiredException extends RuntimeException {
    public InviteCodeRequiredException(Throwable exception) {
        super("Invite code required", exception);
    }
}
