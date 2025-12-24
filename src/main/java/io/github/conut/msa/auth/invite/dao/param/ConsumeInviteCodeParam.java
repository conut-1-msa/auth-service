package io.github.conut.msa.auth.invite.dao.param;

import lombok.Data;

@Data
public class ConsumeInviteCodeParam {
    private int id;
    private String userUuid;
}
