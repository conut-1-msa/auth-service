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
import io.github.conut.msa.auth.integration.member.client.MemberServiceClient;
import io.github.conut.msa.auth.invite.row.InviteCodeRow;
import io.github.conut.msa.auth.invite.service.InviteService;
import io.github.conut.msa.auth.refreshtoken.exception.RefreshTokenMissingException;
import io.github.conut.msa.auth.refreshtoken.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthTxService authTxService;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final CredentialService credentialService;
    private final MemberServiceClient memberServiceClient;
    private final InviteService inviteService;
    private final PasswordEncoder passwordEncoder;

    public AuthTokens login(LoginRequest loginRequest) {
        CredentialRow credentialRow = credentialService.selectActiveCredentialByUserid(loginRequest.getUserid());
        if (credentialRow == null || !passwordEncoder.matches(loginRequest.getPassword(), credentialRow.getPassword())) {
            throw new InvalidCredentialException();
        }
        AuthTokens authTokens = new AuthTokens();
        List<String> roles = memberServiceClient.getMemberRoles(credentialRow.getUserUuid());
        authTokens.setAccessToken(accessTokenService.generateAccessToken(credentialRow.getUserUuid(), roles));
        authTokens.setRefreshToken(refreshTokenService.generateRefreshToken(credentialRow.getUserUuid()));
        return authTokens;
    }

    public boolean isUseridAvailable(String userid) {
        return credentialService.existsByUserid(userid);
    }

    public void register(RegisterRequest registerRequest) {
        String uuid = UUID.randomUUID().toString();
        authTxService.createCredential(uuid, registerRequest);
        InviteCodeRow inviteCode = inviteService.getInviteCode(registerRequest.getInviteCode());
        memberServiceClient.createMember(
            uuid,
            registerRequest.getNickname(),
            inviteCode.getDescription()
        );
        authTxService.activateCredential(uuid, inviteCode.getId());
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
        List<String> roles = memberServiceClient.getMemberRoles(uuid);
        return accessTokenService.generateAccessToken(uuid, roles);
    }
}
