package io.github.conut.msa.auth.invite.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.conut.msa.auth.invite.dao.InviteCodeDAO;
import io.github.conut.msa.auth.invite.dao.param.ConsumeInviteCodeParam;
import io.github.conut.msa.auth.invite.dao.param.CreateInviteCodeParam;
import io.github.conut.msa.auth.invite.dao.param.ReserveInviteCodeParam;
import io.github.conut.msa.auth.invite.dto.CreateInviteCodeRequest;
import io.github.conut.msa.auth.invite.exception.InviteCodeNotFoundException;
import io.github.conut.msa.auth.invite.exception.NoInviteCodeConsumedException;
import io.github.conut.msa.auth.invite.query.InviteCodeListItem;
import io.github.conut.msa.auth.invite.row.InviteCodeRow;
import io.github.conut.msa.auth.invite.util.InviteCodeGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteService {
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

    public void reserveInviteCode(String code, String userUuid) {
        ReserveInviteCodeParam param = new ReserveInviteCodeParam();
        param.setCode(code);
        param.setUserUuid(userUuid);

        int result = inviteCodeDAO.reserveInviteCode(param);
        if (result > 1) {
            throw new IllegalStateException("Multiple invite code reserved for a single code: " + code);
        }
        if (result == 0) {
            throw new InviteCodeNotFoundException();
        }
    }

    public InviteCodeRow getInviteCode(String code) {
        return inviteCodeDAO.selectInviteCodeByCode(code);
    }

    public void consumeInviteCode(int id, String userUuid) {
        ConsumeInviteCodeParam param = new ConsumeInviteCodeParam();
        param.setId(id);
        param.setUserUuid(userUuid);

        int result = inviteCodeDAO.consumeInviteCode(param);
        if (result > 1) {
            throw new IllegalStateException("Multiple invite code consumed for a single id: " + id);
        }
        if (result == 0) {
            throw new NoInviteCodeConsumedException();
        }
    }

    public void releaseReservedInviteCodes() {
        inviteCodeDAO.releaseReservedInviteCodes();
    }
}
