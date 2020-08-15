package com.example.restservice.entities.users.model;

public class UserTokens {
    // private static final long serialVersionUID = 5926468583005150707L;

    public String accessToken; // TODO: make private and use get/set?
    public String refreshToken;

    public UserTokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
