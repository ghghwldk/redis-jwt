package com.tutorial.jwtsecurity.domain.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "token")
@Entity
public class Token {
    @Id
    private String refreshToken;
    private String accessToken;


    @Builder
    public Token(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

    public void updateAccessToken(String token) {
        this.accessToken = token;
    }
}
