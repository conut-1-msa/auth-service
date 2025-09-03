package io.github.conut.msa.auth.auth.service;

import org.springframework.stereotype.Service;

import io.github.conut.msa.auth.accesstoken.service.AccessTokenService;
import io.github.conut.msa.auth.credential.service.CredentialService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccessTokenService accessTokenService;
    private final CredentialService credentialService;

    public String login() {
        return accessTokenService.generateAccessToken();
    }
}
