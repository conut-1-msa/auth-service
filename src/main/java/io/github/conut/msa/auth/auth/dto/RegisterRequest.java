package io.github.conut.msa.auth.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userid;
    private String password;
    private String nickname;
    private String inviteCode;
}
