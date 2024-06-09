package com.tutorial.jwtsecurity.domain.auth.service;

import com.tutorial.jwtsecurity.domain.auth.controller.dto.*;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    @Transactional
    MemberResponseDto signup(MemberRequestDto memberRequestDto);

    @Transactional
    SigninResponseDto signin(SigninRequestDto memberRequestDto, HttpServletResponse response);
}
