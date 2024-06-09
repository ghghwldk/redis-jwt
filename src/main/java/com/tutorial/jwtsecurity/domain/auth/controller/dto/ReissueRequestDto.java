package com.tutorial.jwtsecurity.domain.auth.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReissueRequestDto {
    private String accessToken;
}
