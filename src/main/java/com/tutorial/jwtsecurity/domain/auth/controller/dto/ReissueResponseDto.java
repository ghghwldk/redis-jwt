package com.tutorial.jwtsecurity.domain.auth.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReissueResponseDto {
    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
}
