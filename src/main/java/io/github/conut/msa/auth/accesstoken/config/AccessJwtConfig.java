package io.github.conut.msa.auth.accesstoken.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.conut.msa.auth.common.util.JwtUtil;

@Configuration
public class AccessJwtConfig {
    @Bean(name = "accessJwtUtil")
    JwtUtil accessJwtUtil(@Value("${jwt.access-token-secret}") String accessTokenSecret) {
        return new JwtUtil(accessTokenSecret);
    }
}
