package io.github.conut.msa.auth.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.conut.msa.auth.auth.dto.RegisterRequest;
import io.github.conut.msa.auth.auth.exception.InviteCodeRequiredException;
import io.github.conut.msa.auth.credential.service.CredentialService;
import io.github.conut.msa.auth.invite.exception.InviteCodeNotFoundException;
import io.github.conut.msa.auth.invite.service.InviteService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthTxService {
    private final CredentialService credentialService;
    private final InviteService inviteService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createCredential(String uuid, RegisterRequest registerRequest) {
        try {
            inviteService.reserveInviteCode(registerRequest.getInviteCode(), uuid);
        } catch (InviteCodeNotFoundException exception) {
            throw new InviteCodeRequiredException(exception);
        }
        credentialService.insert(
            uuid,
            registerRequest.getUserid(),
            passwordEncoder.encode(registerRequest.getPassword())
        );
    }

    @Transactional
    public void activateCredential(String uuid, int inviteCodeId) {
        int updated = credentialService.activateCredentialByUserUuid(uuid);
        if (updated != 1) {
            throw new IllegalStateException("Failed to activate credential for user UUID: " + uuid);
        }
        inviteService.consumeInviteCode(inviteCodeId, uuid);
    }
}
