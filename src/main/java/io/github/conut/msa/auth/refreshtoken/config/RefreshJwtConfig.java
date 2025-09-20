package io.github.conut.msa.auth.refreshtoken.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.conut.msa.auth.common.util.JwtUtil;

@Configuration
public class RefreshJwtConfig {
    @Bean(name = "refreshJwtUtil")
    JwtUtil refreshJwtUtil(@Value("${jwt.refresh-token-secret}") String refreshTokenSecret) {
        return new JwtUtil(refreshTokenSecret);
    }
}
