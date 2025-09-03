package io.github.conut.msa.auth.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String userid;
    private String password;
}
