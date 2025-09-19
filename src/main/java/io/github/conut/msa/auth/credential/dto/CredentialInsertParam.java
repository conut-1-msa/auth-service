package io.github.conut.msa.auth.credential.dto;

import lombok.Data;

@Data
public class CredentialInsertParam {
    private String userUuid;
    private String userid;
    private String password;
}
