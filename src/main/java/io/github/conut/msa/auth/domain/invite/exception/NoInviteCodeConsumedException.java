package io.github.conut.msa.auth.domain.invite.exception;

public class NoInviteCodeConsumedException extends RuntimeException {
    public NoInviteCodeConsumedException() {
        super("No invite code consumed");
    }
}
