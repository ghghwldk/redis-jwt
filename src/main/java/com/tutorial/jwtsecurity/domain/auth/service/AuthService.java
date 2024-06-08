package com.tutorial.jwtsecurity.domain.auth.service;

import com.tutorial.jwtsecurity.domain.auth.controller.dto.MemberRequestDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.MemberResponseDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.TokenDto;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    @Transactional
    MemberResponseDto signup(MemberRequestDto memberRequestDto);

    @Transactional
    TokenDto login(MemberRequestDto memberRequestDto);
}
