package com.example.restservice;

public class Token {
    private String accessToken;
    private String refershToken;

    Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refershToken = refreshToken;
    }
}
