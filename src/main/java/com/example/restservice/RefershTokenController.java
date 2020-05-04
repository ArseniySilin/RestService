package com.example.restservice;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.security.SignatureException;

@RestController
public class RefershTokenController {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @PostMapping(
    path = "/refresh",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Response response(@RequestBody Token token) {
    String data = null;

    try {
      String accessToken = jwtTokenUtil.getAccessTokenWithoutHeader(token.accessToken);
      String username = jwtTokenUtil.getUsernameFromToken(accessToken);

      // TODO: verify user by access and refresh token

      boolean isVerified = true;

      if (isVerified) {
        Token refreshedToken = new Token(jwtTokenUtil.generateAccessToken(username), jwtTokenUtil.generateRefreshToken());
        Gson gson = new Gson();
        data = gson.toJson(refreshedToken);

        // TODO: update user in db with new tokens
      } else {
        return new Response(Messages.ERROR.INVALID_TOKEN.code, Messages.ERROR.INVALID_TOKEN.message);
      }
    } catch (SignatureException e) {
      return new Response(Messages.ERROR.INVALID_TOKEN.code, Messages.ERROR.INVALID_TOKEN.message);
    }

    return new Response(Messages.SUCCESS.code, Messages.SUCCESS.message, data);
  }
}
