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
    String data = null;

    try {
      // TODO: replace deprecated methods
      Claims claims = Jwts.parser().setSigningKey(Tokenize.getKey())
        .parseClaimsJws(accessToken).getBody();

      System.out.println("EXP: " + claims.getExpiration());
      username = claims.getSubject();
      boolean isValidDate = Tokenize.validateDate(claims.getExpiration());

      // TODO: remove println
      System.out.println("isValidDate: " + isValidDate);

      if (isValidDate) {
        Token refreshedToken = new Token(Tokenize.generateAccessToken(username), Tokenize.generateRefreshToken());
        Gson gson = new Gson();
        data = gson.toJson(refreshedToken);
      }
    } catch (SignatureException e) {
      return new Response(Messages.ERROR.INVALID_TOKEN.code, Messages.ERROR.INVALID_TOKEN.message);
    }

    return new Response(Messages.SUCCESS.code, data);
  }
}
