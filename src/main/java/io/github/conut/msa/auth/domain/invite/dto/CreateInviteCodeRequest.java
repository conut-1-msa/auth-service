package io.github.conut.msa.auth.domain.invite.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateInviteCodeRequest {
    @Size(max = 30)
    private String description;
}
