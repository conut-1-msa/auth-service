package io.github.conut.msa.auth.accesstoken.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.github.conut.msa.auth.common.util.JwtUtil;

@Service
public class AccessTokenService {
    private final JwtUtil jwtUtil;

    public AccessTokenService(@Qualifier("accessJwtUtil") JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String generateAccessToken(String userUuid, List<String> roles) {
        Map<String, Object> claims = Map.of(
            "uuid", userUuid,
            "roles", roles
        );
        Date thirtyMinutesLater = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));

        return jwtUtil.generateToken(claims, thirtyMinutesLater);
    }
}
