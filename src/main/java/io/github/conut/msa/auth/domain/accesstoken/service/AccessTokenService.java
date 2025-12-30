package io.github.conut.msa.auth.domain.accesstoken.service;

import java.security.PrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class AccessTokenService {
    private final PrivateKey accessTokenPrivateKey;

    public AccessTokenService(@Qualifier("accessTokenPrivateKey") PrivateKey accessTokenPrivateKey) {
        this.accessTokenPrivateKey = accessTokenPrivateKey;
    }

    public String generateAccessToken(String userUuid, List<String> roles) {
        Map<String, Object> claims = Map.of(
            "uuid", userUuid,
            "roles", roles
        );
        Date thirtyMinutesLater = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));

        return Jwts.builder()
            .claims(claims)
            .expiration(thirtyMinutesLater)
            .signWith(accessTokenPrivateKey, Jwts.SIG.RS256)
            .compact();
    }
}
