package com.example.restservice.accounts.controller;

import com.example.restservice.*;
import com.example.restservice.accounts.model.User;
import com.example.restservice.accounts.model.Account;
import com.example.restservice.accounts.service.AccountsService;
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
  public ResponseEntity<CommonResponse> refreshToken(@RequestBody UserTokens userTokens) {
    UserTokens tokens = accountsService.refreshTokens(userTokens);

    return ResponseEntity.ok(new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, tokens));
  }
}
