package io.github.conut.msa.auth.refreshtoken.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.github.conut.msa.auth.common.util.JwtUtil;

@Service
public class RefreshTokenService {
    private final JwtUtil jwtUtil;

    public RefreshTokenService(@Qualifier("refreshJwtUtil") JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String generateRefreshToken(String userUuid) {
        Map<String ,String> claims = Map.of(
            "uuid", userUuid
        );
        Date twoWeeksLater = Date.from(Instant.now().plus(14, ChronoUnit.DAYS));

        return jwtUtil.generateToken(claims, twoWeeksLater);
    }
}
