package com.tutorial.jwtsecurity.domain.auth.service;

import com.tutorial.jwtsecurity.domain.auth.controller.dto.ReissueResponseDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.ReissueRequestDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.SigninResponseDto;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface TokenService {
    Optional<String> get(String refreshToken);

    ReissueResponseDto reissue(ReissueRequestDto reissueRequestDto, HttpServletResponse response);

    SigninResponseDto issue(Authentication authentication, HttpServletResponse response);
}
