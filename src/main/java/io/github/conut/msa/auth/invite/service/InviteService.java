package io.github.conut.msa.auth.invite.service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.github.conut.msa.auth.invite.util.InviteCodeGenerator;
import io.github.conut.msa.auth.invite.vo.InviteCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final RedisTemplate<String, InviteCode> inviteCodeRedisTemplate;
    private final InviteCodeGenerator inviteCodeGenerator;

    public String createInviteCode() {
        String generatedCode = inviteCodeGenerator.generate();
        Instant now = Instant.now();

        String key = "invite:code:" + generatedCode;
        InviteCode inviteCode = new InviteCode(generatedCode, "test", now.plus(7, ChronoUnit.DAYS));
        Duration remaining = Duration.between(now, inviteCode.expiresAt());

        inviteCodeRedisTemplate.opsForValue().set(key, inviteCode, remaining);
        return generatedCode;
    }
}
