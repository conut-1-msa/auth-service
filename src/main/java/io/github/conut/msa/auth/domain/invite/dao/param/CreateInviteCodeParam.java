package io.github.conut.msa.auth.domain.invite.dao.param;

import java.time.Instant;

import lombok.Data;

@Data
public class CreateInviteCodeParam {
    private String code;
    private String description;
    private Instant expiresAt;
}
