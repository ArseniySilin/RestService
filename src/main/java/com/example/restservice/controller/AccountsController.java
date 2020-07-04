package com.example.restservice.controller;

import com.example.restservice.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
//@RequestMapping("/Accounts")
public class AccountsController {
  @Autowired
  private UsersRepository usersRepository;

  public static final String registrationPath = "/Accounts/Registration";

  public AccountsController() {}

  boolean getPasswordSafety(String password) {
    // TODO: add more checks
    return hasMinimumSafetyLength(password);
  }

  boolean hasMinimumSafetyLength(String str) {
    return str != null && str.length() >= 6;
  }

  @PostMapping(
    path = "/Accounts/Registration",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Response registerUser(@RequestBody User user) {
    String username = user.getUsername();

    if (!hasMinimumSafetyLength(username)) {
      return new Response(Messages.ERROR.UNSAFE_USERNAME.code, Messages.ERROR.UNSAFE_USERNAME.message);
    }

    boolean isPasswordSafe = getPasswordSafety(user.getPassword());
    if (!isPasswordSafe) {
      return new Response(Messages.ERROR.UNSAFE_PASSWORD.code, Messages.ERROR.UNSAFE_PASSWORD.message);
    }

    int resultCode = usersRepository.addUser(user.getUsername(), user.getPassword());
    return new Response(resultCode, Messages.getMessageByCode(resultCode));
  }
}
