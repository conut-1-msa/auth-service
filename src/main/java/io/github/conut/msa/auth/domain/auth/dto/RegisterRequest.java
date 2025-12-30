package io.github.conut.msa.auth.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    @Size(min = 6, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String userid;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String password;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{6}$")
    private String inviteCode;
}
