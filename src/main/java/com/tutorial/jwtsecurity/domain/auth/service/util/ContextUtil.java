package com.tutorial.jwtsecurity.domain.auth.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.tutorial.jwtsecurity.domain.auth.service.util.JwtUtil.REFRESH_TOKEN_EXPIRE_TIME;

@Slf4j
public class ContextUtil {
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh-token";

    private ContextUtil() { }

    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw  new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }

        return Long.parseLong(authentication.getName());
    }

    public static void addOnCookie(String refreshToken, HttpServletResponse response){
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken);

        cookie.setMaxAge((int) REFRESH_TOKEN_EXPIRE_TIME);
        cookie.setSecure(true);     // only for https
        cookie.setHttpOnly(true);   // prevent javascript handling
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public static String getRefreshToken(){
        HttpServletRequest request = getHttpServletRequest();
        Optional<String> opt = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(REFRESH_TOKEN_COOKIE_NAME))
                .findFirst()
                .map(Cookie::getValue);

        if(opt.isEmpty()){
            throw new RuntimeException("refresh token is not found");
        }else{
            return opt.get();
        }
    }

    private static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
    }
}