package io.github.conut.msa.auth.member.dto;

import lombok.Data;

@Data
public class MemberCreateRequest {
    private String uuid;
    private String nickname;
    private String description;
}
