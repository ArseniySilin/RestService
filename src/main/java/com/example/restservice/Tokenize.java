package com.example.restservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class Tokenize {
    public static String getAccessToken(String username) {
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

    public static Token getToken(User user) {
        Token token = new Token(getAccessToken(user.getUsername()), "refersh_token");

        return token;
    }
}
