package com.example.restservice.Identity.controller;

import com.example.restservice.*;
import com.google.gson.Gson;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/identity")
public class IdentityController {
  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  public static final String tokenPath = "/identity/token";

  @RequestMapping(value = "/token", method = RequestMethod.POST)
  public ResponseEntity<?> loginUser(@RequestBody User user) throws Exception {
    // authorize user
    int authorizeUserResultCode = usersRepository.authorizeUser(user.getUsername(), user.getPassword());
    String message = Messages.getMessageByCode(authorizeUserResultCode);
    Response response;

    if (authorizeUserResultCode != Messages.SUCCESS.code) {
      response = new Response(authorizeUserResultCode, message);
      return ResponseEntity.ok(response);
    }

    // generate user tokens
    Gson gson = new Gson();
    UserTokens userTokens = usersRepository.generateUserTokens(user.getUsername());
    String data = gson.toJson(userTokens);

    response =  new Response(authorizeUserResultCode, message, data);

    // update user tokens
    int tokensUpdateResultCode = usersRepository.updateUserTokens(
      user.getUsername(),
      userTokens.accessToken,
      userTokens.refreshToken
    );

    if (tokensUpdateResultCode != Messages.SUCCESS.code) {
      response = new Response(Messages.ERROR.DATABASE_ERROR.code, Messages.ERROR.DATABASE_ERROR.message);
    }

    return ResponseEntity.ok(response);
  }

  @PostMapping(
    path = "/refreshtoken",
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
