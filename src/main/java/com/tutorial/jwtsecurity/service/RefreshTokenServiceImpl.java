package com.tutorial.jwtsecurity.service;

import com.tutorial.jwtsecurity.controller.dto.TokenDto;
import com.tutorial.jwtsecurity.controller.dto.TokenRequestDto;
import com.tutorial.jwtsecurity.entity.Token;
import com.tutorial.jwtsecurity.jwt.TokenUtil;
import com.tutorial.jwtsecurity.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenServiceImpl
        implements RefreshTokenService
{
    private final TokenRepository tokenRepository;
    private final TokenUtil tokenUtil;

    @Cacheable(value = "refreshToken", key = "#refreshToken", cacheManager = "cacheManager")
    @Override
    public Optional<String> get(String refreshToken){
        Optional<Token> opt = tokenRepository.findByKey(refreshToken);
        return opt.map(Token::getAccessToken);
    }

    @Override
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        if (!tokenUtil.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        Authentication authentication =
                tokenUtil.getAuthentication(tokenRequestDto.getAccessToken());

        TokenDto tokenDto =
                tokenUtil.generateTokenDto(authentication, tokenRequestDto.getRefreshToken());

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
