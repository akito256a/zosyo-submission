package com.zosyo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @JsonProperty("login_id")
        @NotBlank(message = "ログインIDは必須です")
        String loginId,

        @NotBlank(message = "パスワードは必須です")
        String password
) {}