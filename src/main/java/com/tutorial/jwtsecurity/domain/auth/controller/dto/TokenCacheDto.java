package com.tutorial.jwtsecurity.domain.auth.controller.dto;

import com.tutorial.jwtsecurity.domain.auth.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenCacheDto {
    private String accessToken;
    private String refreshToken;

    public static TokenCacheDto of(Token before){
        return new TokenCacheDto(
                before.getAccessToken(),
                before.getRefreshToken()
        );
    }
}
