package com.tutorial.jwtsecurity.domain.auth.vo;

import com.tutorial.jwtsecurity.domain.auth.controller.dto.ReissueResponseDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.SigninResponseDto;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenVo {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    public ReissueResponseDto toReissueResponseDto(){
        return ReissueResponseDto.builder()
                .grantType(this.grantType)
                .accessToken(this.accessToken)
                .accessTokenExpiresIn(this.accessTokenExpiresIn)
                .build();
    }

    public SigninResponseDto toSigninResponseDto(){
        return SigninResponseDto.builder()
                .grantType(this.grantType)
                .accessToken(this.accessToken)
                .accessTokenExpiresIn(this.accessTokenExpiresIn)
                .build();
    }
}
