package com.tutorial.jwtsecurity.domain.auth.controller;


import com.tutorial.jwtsecurity.domain.auth.controller.dto.*;
import com.tutorial.jwtsecurity.domain.auth.service.AuthService;
import com.tutorial.jwtsecurity.domain.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDto> sigin
            (@RequestBody SigninRequestDto dto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.signin(dto, response));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponseDto> reissue
            (@RequestBody ReissueRequestDto reissueRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(tokenService.reissue(reissueRequestDto, response));
    }
}
