package com.example.restservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.UUID;

public class Tokenize {
    public static String generateAccessToken(String username) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);

        String jws = Jwts.builder()
            .setSubject(username)
            .setExpiration(calendar.getTime())
            .signWith(key)
            .compact();

        return jws;
    }

    public static String generateRefreshToken() {
        String refreshTokenUUID = UUID.randomUUID().toString();
        return refreshTokenUUID;
    }



    public static Token generateToken(User user) {
        Token token = new Token(generateAccessToken(user.getUsername()), generateRefreshToken());

        return token;
    }
}
