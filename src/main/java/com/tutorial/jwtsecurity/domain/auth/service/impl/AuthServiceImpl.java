package com.tutorial.jwtsecurity.domain.auth.service.impl;

import com.tutorial.jwtsecurity.domain.auth.controller.dto.MemberRequestDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.MemberResponseDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.TokenDto;
import com.tutorial.jwtsecurity.domain.auth.entity.Member;
import com.tutorial.jwtsecurity.domain.auth.service.AuthService;
import com.tutorial.jwtsecurity.domain.auth.service.TokenService;
import com.tutorial.jwtsecurity.global.security.JwtUtil;
import com.tutorial.jwtsecurity.domain.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Transactional
    @Override
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    @Override
    public TokenDto login(MemberRequestDto memberRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                memberRequestDto.toAuthentication();
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto =
                jwtUtil.generateTokenDto(authentication, null);

        tokenService.save(tokenDto);

        return tokenDto;
    }
}
