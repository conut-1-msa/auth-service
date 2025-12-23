package io.github.conut.msa.auth.invite.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.github.conut.msa.auth.invite.dao.InviteCodeDAO;
import io.github.conut.msa.auth.invite.dao.param.CreateInviteCodeParam;
import io.github.conut.msa.auth.invite.dto.CreateInviteCodeRequest;
import io.github.conut.msa.auth.invite.exception.InviteCodeNotFoundException;
import io.github.conut.msa.auth.invite.query.InviteCodeListItem;
import io.github.conut.msa.auth.invite.util.InviteCodeGenerator;
import io.github.conut.msa.auth.invite.vo.InviteCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final RedisTemplate<String, InviteCode> inviteCodeRedisTemplate;
    private final InviteCodeGenerator inviteCodeGenerator;
    private final InviteCodeDAO inviteCodeDAO;

    public String createInviteCode(CreateInviteCodeRequest createInviteCodeRequest) {
        String code = inviteCodeGenerator.generate();

        CreateInviteCodeParam createInviteCodeParam = new CreateInviteCodeParam();
        createInviteCodeParam.setCode(code);
        createInviteCodeParam.setDescription(createInviteCodeRequest.getDescription());
        createInviteCodeParam.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));

        inviteCodeDAO.insertInviteCode(createInviteCodeParam);

        return code;
    }

    public List<InviteCodeListItem> getInviteCodeList() {
        return inviteCodeDAO.selectInviteCodes();
    }

    public InviteCode getAndDeleteInviteCode(String inviteCode) {
        String key = "invite:code:" + inviteCode;
        InviteCode inviteCodeValue = inviteCodeRedisTemplate.opsForValue().getAndDelete(key);
        if (inviteCodeValue == null) {
            throw new InviteCodeNotFoundException();
        }
        return inviteCodeValue;
    }
}
