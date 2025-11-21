package io.github.conut.msa.auth.invite.service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public List<InviteCode> getInviteCodeList() {
        Set<String> keys = inviteCodeRedisTemplate.keys("invite:code:*");
        ArrayList<InviteCode> list = new ArrayList<>();
        for (var key: keys) {
            InviteCode inviteCode = inviteCodeRedisTemplate.opsForValue().get(key);
            if (inviteCode != null) {
                list.add(inviteCode);
            }
        }
        return list;
    }
}
