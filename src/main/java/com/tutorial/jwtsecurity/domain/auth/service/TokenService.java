package com.tutorial.jwtsecurity.domain.auth.service;

import com.tutorial.jwtsecurity.domain.auth.controller.dto.TokenDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.TokenRequestDto;

import java.util.Optional;

public interface TokenService {
    Optional<String> get(String refreshToken);

    TokenDto reissue(TokenRequestDto tokenRequestDto);

    void save(TokenDto dto);
}
