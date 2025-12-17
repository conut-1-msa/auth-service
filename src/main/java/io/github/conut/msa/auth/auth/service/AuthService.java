package io.github.conut.msa.auth.auth.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.conut.msa.auth.accesstoken.service.AccessTokenService;
import io.github.conut.msa.auth.auth.dto.AuthTokens;
import io.github.conut.msa.auth.auth.dto.LoginRequest;
import io.github.conut.msa.auth.auth.dto.RegisterRequest;
import io.github.conut.msa.auth.auth.exception.CredentialNotActiveException;
import io.github.conut.msa.auth.auth.exception.InvalidCredentialException;
import io.github.conut.msa.auth.credential.dto.CredentialRow;
import io.github.conut.msa.auth.credential.service.CredentialService;
import io.github.conut.msa.auth.invite.service.InviteService;
import io.github.conut.msa.auth.invite.vo.InviteCode;
import io.github.conut.msa.auth.member.service.MemberService;
import io.github.conut.msa.auth.refreshtoken.exception.RefreshTokenMissingException;
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
        CredentialRow credentialRow = credentialService.selectActiveCredentialByUserid(loginRequest.getUserid());
        if (credentialRow == null || !passwordEncoder.matches(loginRequest.getPassword(), credentialRow.getPassword())) {
            throw new InvalidCredentialException();
        }
        AuthTokens authTokens = new AuthTokens();
        List<String> roles = memberService.getMemberRoles(credentialRow.getUserUuid());
        authTokens.setAccessToken(accessTokenService.generateAccessToken(credentialRow.getUserUuid(), roles));
        authTokens.setRefreshToken(refreshTokenService.generateRefreshToken(credentialRow.getUserUuid()));
        return authTokens;
    }

    public void register(RegisterRequest registerRequest) {
        InviteCode inviteCode = inviteService.getAndDeleteInviteCode(registerRequest.getInviteCode());
        String uuid = UUID.randomUUID().toString();
        credentialService.insert(
            uuid,
            registerRequest.getUserid(),
            passwordEncoder.encode(registerRequest.getPassword())
        );
        memberService.createMember(
            uuid,
            registerRequest.getNickname(),
            inviteCode.description()
        );
        int updated = credentialService.activateCredentialByUserUuid(uuid);
        if (updated != 1) {
            throw new IllegalStateException("Failed to activate credential for user UUID: " + uuid);
        }
    }

    public String refreshAccessToken(String refreshToken) {
        if (refreshToken == null) {
            throw new RefreshTokenMissingException();
        }
        Claims claims = refreshTokenService.parseRefreshToken(refreshToken);
        String uuid = claims.get("uuid", String.class);
        if (!credentialService.isActive(uuid)) {
            throw new CredentialNotActiveException();
        }
        List<String> roles = memberService.getMemberRoles(uuid);
        return accessTokenService.generateAccessToken(uuid, roles);
    }
}
