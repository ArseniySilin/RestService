package com.example.restservice.entities.users.controller;

import com.example.restservice.*;
import com.example.restservice.entities.common.CommonResponse;
import com.example.restservice.entities.users.model.User;
import com.example.restservice.entities.users.model.Account;
import com.example.restservice.entities.users.model.UserTokens;
import com.example.restservice.entities.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
  @Autowired
  private UsersService usersService;

  public static final String registrationPath = "/users/registration";
  public static final String tokenPath = "/users/token";
  public static final String refreshTokenPath = "/users/refreshtoken";

  public UsersController() {}

  @PostMapping(
    path = "/registration",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<CommonResponse> registerAccount(@RequestBody Account account) {
    User user = new User(account.getUsername(), account.getPassword());
    usersService.addUser(user);

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
    UserTokens tokens = usersService.getTokens(account);

    return ResponseEntity.ok(new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, tokens));
  }

  @PostMapping(
    path = "/refreshtoken",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<CommonResponse> refreshToken(@RequestBody UserTokens userTokens) {
    UserTokens tokens = usersService.refreshTokens(userTokens);

    return ResponseEntity.ok(new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, tokens));
  }
}
