package com.example.restservice;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.security.SignatureException;

@RestController
public class RefershTokenController {
  @PostMapping(
      path = "/refresh",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Response response(@RequestBody Token token) {
    String accessToken = token.accessToken;
    String refreshToken = token.refershToken;
    String username;

    try {
      Claims claims = Jwts.parser().setSigningKey(Tokenize.getKey())
        .parseClaimsJws(accessToken).getBody();

      System.out.println(claims.getExpiration());
      username = claims.getSubject();

      // TODO: validate exp data
    } catch (SignatureException e) {
      return new Response(Messages.ERROR.INVALID_TOKEN.code, Messages.ERROR.INVALID_TOKEN.message);
    }

    Token refreshedToken = new Token(Tokenize.generateAccessToken(username), Tokenize.generateRefreshToken());
    Gson gson = new Gson();
    String data = gson.toJson(refreshedToken);

    return new Response(Messages.SUCCESS.code, data);
  }
}
