package com.zpop.web.dto.admin.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AdminLoginDto {
    @NotBlank
    private String name;
    @NotBlank
    private String pwd;
}
