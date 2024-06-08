package com.tutorial.jwtsecurity.service;

import com.tutorial.jwtsecurity.controller.dto.TokenDto;
import com.tutorial.jwtsecurity.controller.dto.TokenRequestDto;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<String> get(String refreshToken);

    TokenDto reissue(TokenRequestDto tokenRequestDto);

    void save(TokenDto dto);
}
