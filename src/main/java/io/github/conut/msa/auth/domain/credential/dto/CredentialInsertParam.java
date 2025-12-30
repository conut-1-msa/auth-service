package io.github.conut.msa.auth.domain.credential.dto;

import lombok.Data;

@Data
public class CredentialInsertParam {
    private String userUuid;
    private String userid;
    private String password;
}
