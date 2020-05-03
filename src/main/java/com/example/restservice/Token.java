package com.example.restservice;

public class Token {
    // private static final long serialVersionUID = 5926468583005150707L;

    public String accessToken; // TODO: make private and use get/set?
    public String refershToken;

    Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refershToken = refreshToken;
    }
}
