package io.github.conut.msa.auth.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.github.conut.msa.auth.accesstoken.service.AccessTokenService;
import io.github.conut.msa.auth.auth.dto.LoginRequest;
import io.github.conut.msa.auth.credential.dto.CredentialRow;
import io.github.conut.msa.auth.credential.service.CredentialService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccessTokenService accessTokenService;
    private final CredentialService credentialService;

    public String login(LoginRequest loginRequest) {
        CredentialRow credentialRow = credentialService.selectByUserid(loginRequest.getUserid());
        if (credentialRow == null || !credentialRow.getPassword().equals(credentialRow.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return accessTokenService.generateAccessToken(credentialRow.getUserUuid());
    }
}
