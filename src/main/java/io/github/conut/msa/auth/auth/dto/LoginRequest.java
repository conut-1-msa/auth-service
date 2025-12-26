package io.github.conut.msa.auth.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String userid;

    @NotBlank
    private String password;
}
