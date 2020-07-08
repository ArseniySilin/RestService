package com.example.restservice;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
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

  public String tokenHeader = "Bearer ";

  public String getAccessTokenWithoutHeader (String accessToken) {
    if (accessToken.startsWith(tokenHeader)) return accessToken.substring(tokenHeader.length());

    return accessToken;
  }

  //retrieve username from jwt token
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public String getUserIdFromToken(String token) {
    return getClaimFromToken(token, Claims::getId);
  }

  public String getUserIdFromBearerToken(String token) {
    String userId = null;

    if (token.startsWith(tokenHeader)) {
      String tokenWithoutHeader = token.substring(tokenHeader.length());
      userId = this.getUserIdFromToken(tokenWithoutHeader);
    }

    return userId;
  }

  //retrieve expiration date from jwt token
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  // for retrieveing any information from token we will need the secret key
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  //check if the token has expired
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

  public String generateAccessToken(String username, String id) {
    Map<String, Object> claims = new HashMap<>();

    String jws = Jwts.builder()
      .setClaims(claims)
      .setSubject(username)
      .setId(id)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
      .signWith(SignatureAlgorithm.HS256, secret).compact();

    return jws;
  }

  public String generateRefreshToken() {
    return UUID.randomUUID().toString();
  }

  //validate token
  public Boolean validateToken(String token, User user) {
    final String username = getUsernameFromToken(token);
    return (username.equals(user.getUsername()) && !isTokenExpired(token));
  }
  public Boolean validateToken(String token, String username) {
    final String usernameFromToken = getUsernameFromToken(token);
    return (usernameFromToken.equals(username) && !isTokenExpired(token));
  }
}