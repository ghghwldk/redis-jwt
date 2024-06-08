package com.tutorial.jwtsecurity.domain.auth.controller;


import com.tutorial.jwtsecurity.domain.auth.controller.dto.MemberRequestDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.MemberResponseDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.TokenDto;
import com.tutorial.jwtsecurity.domain.auth.controller.dto.TokenRequestDto;
import com.tutorial.jwtsecurity.domain.auth.service.AuthService;
import com.tutorial.jwtsecurity.domain.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(tokenService.reissue(tokenRequestDto));
    }
}
