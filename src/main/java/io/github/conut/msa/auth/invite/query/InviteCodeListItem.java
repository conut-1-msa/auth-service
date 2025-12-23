package io.github.conut.msa.auth.invite.query;

import java.time.Instant;

import lombok.Data;

@Data
public class InviteCodeListItem {
    private int id;
    private String code;
    private String description;
    private Instant expiresAt;
}
