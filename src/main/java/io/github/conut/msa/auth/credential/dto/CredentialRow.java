package io.github.conut.msa.auth.credential.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CredentialRow {
    private int id;
    private String userUuid;
    private String userid;
    private String password;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime failedAt;
    private String batchId;
}
