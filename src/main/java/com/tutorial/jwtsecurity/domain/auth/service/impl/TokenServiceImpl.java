package com.tutorial.jwtsecurity.domain.auth.service.impl;

import com.tutorial.jwtsecurity.domain.auth.controller.dto.ReissueResponseDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.SigninResponseDto;
import com.tutorial.jwtsecurity.domain.auth.vo.TokenVo;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.ReissueRequestDto;
import com.tutorial.jwtsecurity.domain.auth.entity.Token;
import com.tutorial.jwtsecurity.domain.auth.repository.TokenRepository;
import com.tutorial.jwtsecurity.domain.auth.service.TokenService;
import com.tutorial.jwtsecurity.domain.auth.service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.tutorial.jwtsecurity.domain.auth.service.util.ContextUtil.addOnCookie;
import static com.tutorial.jwtsecurity.domain.auth.service.util.ContextUtil.getRefreshToken;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TokenServiceImpl
        implements TokenService
{
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;

    @Cacheable(value = "refreshToken", key = "#refreshToken", cacheManager = "cacheManager")
    @Override
    public Optional<String> exist(String refreshToken){
        Optional<Token> opt = tokenRepository.findById(refreshToken);
        return opt.map(Token::getRefreshToken);
    }

    @Override
    public ReissueResponseDto reissue
            (ReissueRequestDto reissueRequestDto, HttpServletResponse response){
        String refreshToken = getRefreshToken();

        if(exist(refreshToken).isEmpty()){
            throw new RuntimeException("로그인된 사용자가 아닙니다.");
        }

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        Authentication authentication =
                jwtUtil.getAuthentication(reissueRequestDto.getAccessToken());

        TokenVo tokenVo =
                jwtUtil.generateTokenVo(authentication, refreshToken);

        this.update(tokenVo);

        addOnCookie(tokenVo.getRefreshToken(), response);
        return tokenVo.toReissueResponseDto();
    }

    @CachePut(value = "refreshToken", key = "#refreshToken", cacheManager = "cacheManager")
    private String update(TokenVo tokenVo) {
        Token found = tokenRepository.findById(tokenVo.getRefreshToken())
                .orElseThrow(EntityNotFoundException::new);

        found.updateAccessToken(tokenVo.getAccessToken());

        return found.getAccessToken();
    }

    @Override
    public SigninResponseDto issue(Authentication authentication, HttpServletResponse response) {
        TokenVo vo =
                jwtUtil.generateTokenVo(authentication, null);

        Token token = Token.builder()
                .refreshToken(vo.getRefreshToken())
                .accessToken(vo.getAccessToken())
                .build();

        tokenRepository.save(token);

        addOnCookie(vo.getRefreshToken(), response);
        return vo.toSigninResponseDto();
    }


}
