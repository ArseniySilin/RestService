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

  @Autowired
  private UsersRepository usersRepository;

  @PostMapping(
    path = "/refresh",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Response response(@RequestBody UserTokens userTokens) {
    String data;

    try {
      String accessToken = jwtTokenUtil.getAccessTokenWithoutHeader(userTokens.accessToken);
      String refreshToken = userTokens.refreshToken;
      String username = jwtTokenUtil.getUsernameFromToken(accessToken);

      // validate tokens
      int tokensValidationResultCode = usersRepository.validateUserTokens(username, accessToken, refreshToken);

      if (tokensValidationResultCode != Messages.SUCCESS.code) {
        return new Response(Messages.ERROR.INVALID_TOKEN.code, Messages.ERROR.INVALID_TOKEN.message);
      }

      // update user tokens
      UserTokens refreshedUserTokens = new UserTokens(
        jwtTokenUtil.generateAccessToken(username),
        jwtTokenUtil.generateRefreshToken()
      );

      Gson gson = new Gson();
      data = gson.toJson(refreshedUserTokens);

      int tokensUpdateResultCode =
        usersRepository.updateUserTokens(username, refreshedUserTokens.accessToken, refreshedUserTokens.refreshToken);

      if (tokensUpdateResultCode != Messages.SUCCESS.code) {
        return new Response(Messages.ERROR.DATABASE_ERROR.code, Messages.ERROR.DATABASE_ERROR.message);
      }
    } catch (SignatureException e) {
      return new Response(Messages.ERROR.INVALID_TOKEN.code, Messages.ERROR.INVALID_TOKEN.message);
    }

    return new Response(Messages.SUCCESS.code, Messages.SUCCESS.message, data);
  }
}
