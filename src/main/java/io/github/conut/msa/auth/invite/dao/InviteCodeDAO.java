package io.github.conut.msa.auth.invite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.github.conut.msa.auth.invite.dao.param.ConsumeInviteCodeParam;
import io.github.conut.msa.auth.invite.dao.param.CreateInviteCodeParam;
import io.github.conut.msa.auth.invite.dao.param.ReserveInviteCodeParam;
import io.github.conut.msa.auth.invite.query.InviteCodeListItem;
import io.github.conut.msa.auth.invite.row.InviteCodeRow;

@Mapper
public interface InviteCodeDAO {
    void insertInviteCode(CreateInviteCodeParam createInviteCodeParam);
    List<InviteCodeListItem> selectInviteCodes();
    int reserveInviteCode(ReserveInviteCodeParam reserveInviteCodeParam);
    InviteCodeRow selectInviteCodeByCode(String code);
    int consumeInviteCode(ConsumeInviteCodeParam consumeInviteCodeParam);
}
