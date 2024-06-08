package com.tutorial.jwtsecurity.domain.auth.service.impl;

import com.tutorial.jwtsecurity.domain.auth.controller.dto.TokenDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.TokenRequestDto;
import com.tutorial.jwtsecurity.domain.auth.entity.Token;
import com.tutorial.jwtsecurity.domain.auth.repository.TokenRepository;
import com.tutorial.jwtsecurity.domain.auth.service.TokenService;
import com.tutorial.jwtsecurity.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenServiceImpl
        implements TokenService
{
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;

    @Cacheable(value = "refreshToken", key = "#refreshToken", cacheManager = "cacheManager")
    @Override
    public Optional<String> get(String refreshToken){
        Optional<Token> opt = tokenRepository.findByKey(refreshToken);
        return opt.map(Token::getAccessToken);
    }

    @Override
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        if (!jwtUtil.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        Authentication authentication =
                jwtUtil.getAuthentication(tokenRequestDto.getAccessToken());

        TokenDto tokenDto =
                jwtUtil.generateTokenDto(authentication, tokenRequestDto.getRefreshToken());

        this.update(tokenDto);

        return tokenDto;
    }

    @CachePut(value = "refreshToken", key = "#refreshToken", cacheManager = "cacheManager")
    private String update(TokenDto tokenDto) {
        Token found = tokenRepository.findById(tokenDto.getRefreshToken())
                .orElseThrow(EntityNotFoundException::new);

        found.updateAccessToken(tokenDto.getAccessToken());

        return found.getAccessToken();
    }

    @Override
    public void save(TokenDto dto) {
        Token token = Token.builder()
                .refreshToken(dto.getRefreshToken())
                .accessToken(dto.getAccessToken())
                .build();

        tokenRepository.save(token);
    }
}
