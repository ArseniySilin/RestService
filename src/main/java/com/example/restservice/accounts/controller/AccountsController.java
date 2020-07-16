package com.example.restservice.accounts.controller;

import com.example.restservice.*;
import com.example.restservice.accounts.model.User;
import com.example.restservice.accounts.model.Account;
import com.example.restservice.accounts.service.AccountsService;
import com.google.gson.Gson;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/Accounts")
public class AccountsController {
  @Autowired
  private AccountsService accountsService;

  public static final String registrationPath = "/Accounts/Registration";

  public AccountsController() {}

  @PostMapping(
    path = "/Registration",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<CommonResponse> registerAccount(@RequestBody Account account) {
    User user = new User(account.getUsername(), account.getPassword());
    accountsService.addUser(user);

    return ResponseEntity.ok(new CommonResponse(
      Messages.SUCCESS.message,
      Messages.SUCCESS.code,
      null,
      null
    ));
  }

  @PostMapping(
    path = "/token",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<CommonResponse> login(@RequestBody Account account) throws Exception {
    UserTokens tokens = accountsService.getTokens(account);

    return ResponseEntity.ok(new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, tokens));
  }

  @PostMapping(
    path = "/refreshtoken",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Response refreshToken(@RequestBody UserTokens userTokens) {
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
