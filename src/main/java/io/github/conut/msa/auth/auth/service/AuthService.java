package io.github.conut.msa.auth.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.github.conut.msa.auth.accesstoken.service.AccessTokenService;
import io.github.conut.msa.auth.auth.dto.AuthTokens;
import io.github.conut.msa.auth.auth.dto.LoginRequest;
import io.github.conut.msa.auth.auth.dto.RegisterRequest;
import io.github.conut.msa.auth.credential.dto.CredentialRow;
import io.github.conut.msa.auth.credential.service.CredentialService;
import io.github.conut.msa.auth.member.service.MemberService;
import io.github.conut.msa.auth.refreshtoken.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final CredentialService credentialService;
    private final MemberService memberService;

    public AuthTokens login(LoginRequest loginRequest) {
        CredentialRow credentialRow = credentialService.selectByUserid(loginRequest.getUserid());
        if (credentialRow == null || !credentialRow.getPassword().equals(loginRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        AuthTokens authTokens = new AuthTokens();
        authTokens.setAccessToken(accessTokenService.generateAccessToken(credentialRow.getUserUuid()));
        authTokens.setRefreshToken(refreshTokenService.generateRefreshToken(credentialRow.getUserUuid()));
        return authTokens;
    }

    public void register(RegisterRequest registerRequest) {
        String uuid = memberService.createMember(registerRequest.getNickname()).getUuid();
        credentialService.insert(uuid, registerRequest.getUserid(), registerRequest.getPassword());
    }
}
