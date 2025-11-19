package io.github.conut.msa.auth.invite.vo;

import java.time.Instant;

public record InviteCode(
    String code,
    String description,
    Instant expiresAt
) {}
