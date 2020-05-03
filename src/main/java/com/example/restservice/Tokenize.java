package com.example.restservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class Tokenize {
    @Value("${jwt.secret}")
    private static String secret;

    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static Key getKey() {
        return key;
    }

    public static boolean validateDate(Date date) {
        Date now = new Date();

        return now.after(date);
    }

    public static String generateAccessToken(String username) {
        System.out.println("generateAccessToken secret: " + secret);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
//        String jws = Jwts.builder()
//            .setSubject(username)
//            .setExpiration(calendar.getTime())
//            .signWith(key)
//            .compact();
        String jws = Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(calendar.getTime())
          .signWith(SignatureAlgorithm.HS256, "ZVOxS3GE2QHDCucwfp8W/ePCUBWYOHAJ8Fercd9i+gg=").compact(); // TODO: fix

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
