package io.github.conut.msa.auth.domain.invite.vo;

import java.time.Instant;

public record InviteCode(
    String code,
    String description,
    Instant expiresAt
) {}
