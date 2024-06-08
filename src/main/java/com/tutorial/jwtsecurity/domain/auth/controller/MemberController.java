package com.tutorial.jwtsecurity.domain.auth.controller;

import com.tutorial.jwtsecurity.domain.auth.controller.dto.MemberResponseDto;
import com.tutorial.jwtsecurity.domain.auth.service.MemberServiceImpl;
import com.tutorial.jwtsecurity.global.security.SpringSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberServiceImpl memberServiceImpl;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMemberInfoById() {
        return ResponseEntity.ok(memberServiceImpl.findMemberInfoById(SpringSecurityUtil.getCurrentMemberId()));
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberResponseDto> findMemberInfoByEmail(@PathVariable String email) {
        return ResponseEntity.ok(memberServiceImpl.findMemberInfoByEmail(email));
    }
}
