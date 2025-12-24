package io.github.conut.msa.auth.invite.dao.param;

import lombok.Data;

@Data
public class ReserveInviteCodeParam {
    private String code;
    private String userUuid;
}
