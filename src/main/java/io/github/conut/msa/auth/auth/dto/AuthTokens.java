package io.github.conut.msa.auth.auth.dto;

import lombok.Data;

@Data
public class AuthTokens {
    private String accessToken;
    private String refreshToken;
}
