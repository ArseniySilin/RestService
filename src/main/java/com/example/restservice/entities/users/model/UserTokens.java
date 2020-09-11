package com.example.restservice.entities.users.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserTokens {
    // private static final long serialVersionUID = 5926468583005150707L;

    public String accessToken; // TODO: make private and use get/set?
    public String refreshToken;
}
