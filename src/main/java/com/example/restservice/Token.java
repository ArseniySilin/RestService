package com.example.restservice;

public class Token {
    public String accessToken;
    public String refershToken;

    Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refershToken = refreshToken;
    }
}
