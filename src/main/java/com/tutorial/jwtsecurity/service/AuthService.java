package com.tutorial.jwtsecurity.service;

import com.tutorial.jwtsecurity.controller.dto.MemberRequestDto;
import com.tutorial.jwtsecurity.controller.dto.MemberResponseDto;
import com.tutorial.jwtsecurity.controller.dto.TokenRequestDto;
import com.tutorial.jwtsecurity.controller.dto.TokenDto;
import com.tutorial.jwtsecurity.entity.Member;
import com.tutorial.jwtsecurity.entity.Token;
import com.tutorial.jwtsecurity.jwt.TokenUtil;
import com.tutorial.jwtsecurity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                memberRequestDto.toAuthentication();
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto =
                tokenUtil.generateTokenDto(authentication, null);

        refreshTokenService.save(tokenDto);

        return tokenDto;
    }
}
