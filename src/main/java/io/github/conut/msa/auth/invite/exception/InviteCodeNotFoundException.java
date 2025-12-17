package io.github.conut.msa.auth.invite.exception;

public class InviteCodeNotFoundException extends RuntimeException {
    public InviteCodeNotFoundException() {
        super("Invite code not found");
    }
}
