package io.github.conut.msa.auth.auth.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.github.conut.msa.auth.accesstoken.service.AccessTokenService;
import io.github.conut.msa.auth.auth.dto.AuthTokens;
import io.github.conut.msa.auth.auth.dto.LoginRequest;
import io.github.conut.msa.auth.auth.dto.RegisterRequest;
import io.github.conut.msa.auth.credential.dto.CredentialRow;
import io.github.conut.msa.auth.credential.service.CredentialService;
import io.github.conut.msa.auth.invite.service.InviteService;
import io.github.conut.msa.auth.invite.vo.InviteCode;
import io.github.conut.msa.auth.member.service.MemberService;
import io.github.conut.msa.auth.refreshtoken.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final CredentialService credentialService;
    private final MemberService memberService;
    private final InviteService inviteService;
    private final PasswordEncoder passwordEncoder;

    public AuthTokens login(LoginRequest loginRequest) {
        CredentialRow credentialRow = credentialService.selectByUserid(loginRequest.getUserid());
        if (credentialRow == null || !passwordEncoder.matches(loginRequest.getPassword(), credentialRow.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        AuthTokens authTokens = new AuthTokens();
        List<String> roles = memberService.getMemberRoles(credentialRow.getUserUuid());
        authTokens.setAccessToken(accessTokenService.generateAccessToken(credentialRow.getUserUuid(), roles));
        authTokens.setRefreshToken(refreshTokenService.generateRefreshToken(credentialRow.getUserUuid()));
        return authTokens;
    }

    public void register(RegisterRequest registerRequest) {
        InviteCode inviteCode = inviteService.getAndDeleteInviteCode(registerRequest.getInviteCode());
        if (inviteCode == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String uuid = memberService
            .createMember(
                registerRequest.getNickname(),
                inviteCode.description()
            )
            .getUuid();
        credentialService.insert(
            uuid,
            registerRequest.getUserid(),
            passwordEncoder.encode(registerRequest.getPassword())
        );
    }

    public String refreshAccessToken(String refreshToken) {
        if (refreshToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Claims claims = refreshTokenService.parseRefreshToken(refreshToken);
        String uuid = claims.get("uuid", String.class);
        List<String> roles = memberService.getMemberRoles(uuid);
        return accessTokenService.generateAccessToken(uuid, roles);
    }
}
