package com.tutorial.jwtsecurity.domain.auth.service;

import com.tutorial.jwtsecurity.domain.auth.controller.dto.MemberResponseDto;

public interface MemberService{
    MemberResponseDto findMemberInfoById(Long memberId);

    MemberResponseDto findMemberInfoByEmail(String email);
}
