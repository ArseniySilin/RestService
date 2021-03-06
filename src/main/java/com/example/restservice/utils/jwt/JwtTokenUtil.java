package com.example.restservice.utils.jwt;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
  private static final long serialVersionUID = -7496936772985883565L;

  @Value("${jwt.secret}")
  private String secret;

  public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

  public static final String tokenHeader = "Bearer ";

  public String getAccessTokenWithoutHeader (String accessToken) {
    if (accessToken.startsWith(tokenHeader)) return accessToken.substring(tokenHeader.length());

    return accessToken;
  }

  public String getUsernameFromToken(String token) {
    if (token.startsWith(tokenHeader)) {
      String tokenWithoutHeader = token.substring(tokenHeader.length());
      return getClaimFromToken(tokenWithoutHeader, Claims::getSubject);
    }

    return getClaimFromToken(token, Claims::getSubject);
  }

  public String getUserKeyFromToken(String token) {
    final Claims claims = getAllClaimsFromToken(getAccessTokenWithoutHeader(token));
    return claims.get("userKey", String.class);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);

    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  public Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateAccessToken(String username) {
    Map<String, Object> claims = new HashMap<>();

    String jws = Jwts.builder()
      .setClaims(claims)
      .setSubject(username)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
      .signWith(SignatureAlgorithm.HS256, secret).compact();

    return jws;
  }

  public String generateAccessToken(String username, int id, String userKey) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userKey", userKey);

    String jws = Jwts.builder()
      .setClaims(claims)
      .setSubject(username)
      .setId(Integer.toString(id))
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
      .signWith(SignatureAlgorithm.HS256, secret).compact();

    return jws;
  }

  public String generateAccessToken(String username, String userKey) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userKey", userKey);

    String jws = Jwts.builder()
      .setClaims(claims)
      .setSubject(username)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
      .signWith(SignatureAlgorithm.HS256, secret).compact();

    return jws;
  }

  public String generateRefreshToken() {
    return UUID.randomUUID().toString();
  }

  public Boolean validateToken(String token, String username) {
    final String usernameFromToken = getUsernameFromToken(token);
    return (usernameFromToken.equals(username) && !isTokenExpired(token));
  }
}