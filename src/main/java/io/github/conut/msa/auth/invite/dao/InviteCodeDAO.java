package io.github.conut.msa.auth.invite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.github.conut.msa.auth.invite.dao.param.CreateInviteCodeParam;
import io.github.conut.msa.auth.invite.query.InviteCodeListItem;

@Mapper
public interface InviteCodeDAO {
    void insertInviteCode(CreateInviteCodeParam createInviteCodeParam);
    List<InviteCodeListItem> selectInviteCodes();
}
