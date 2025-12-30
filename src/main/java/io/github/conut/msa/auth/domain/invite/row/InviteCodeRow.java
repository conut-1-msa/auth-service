package io.github.conut.msa.auth.domain.invite.row;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InviteCodeRow {
    private int id;
    private String code;
    private String description;
    private String status;
    private LocalDateTime expiresAt;
    private LocalDateTime consumedAt;
    private String consumedBy;
}
