package com.example.restservice.accounts.service;

import com.example.restservice.JwtTokenUtil;
import com.example.restservice.Messages;
import com.example.restservice.Response;
import com.example.restservice.UserTokens;
import com.example.restservice.accounts.exceptions.AccountsException;
import com.example.restservice.accounts.model.Account;
import com.example.restservice.accounts.model.User;
import com.example.restservice.accounts.repository.UsersRepository;
import com.example.restservice.execptions.EntityAlreadyExistsException;
import com.google.gson.Gson;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

  @Autowired
  UsersRepository usersRepository;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  private boolean getPasswordSafety(String password) {
    return hasMinimumSafetyLength(password);
  }

  boolean hasMinimumSafetyLength(String str) {
    return str != null && str.length() >= 6;
  }

  public void addUser(User user) throws EntityAlreadyExistsException, AccountsException {
    if (usersRepository.getUserByUserName(user.getUsername()) != null) {
      throw new EntityAlreadyExistsException(User.class);
    }

    String username = user.getUsername();
    String password = user.getPassword();

    if (!hasMinimumSafetyLength(username)) {
      throw new AccountsException(Messages.ERROR.UNSAFE_USERNAME.message);
    }

    boolean isPasswordSafe = getPasswordSafety(password);
    if (!isPasswordSafe) {
      throw new AccountsException(Messages.ERROR.UNSAFE_PASSWORD.message);
    }

    usersRepository.addUser(user);
  }

  public UserTokens getTokens(Account account) throws AccountsException {
    usersRepository.authorizeUser(account.getUsername(), account.getPassword());

    UserTokens userTokens = usersRepository.generateUserTokens(account.getUsername());
    String data = new Gson().toJson(userTokens);

    usersRepository.updateUserTokens(
      account.getUsername(),
      userTokens.accessToken,
      userTokens.refreshToken
    );

    return userTokens;
  }

  public UserTokens refreshTokens(UserTokens tokens) throws AccountsException {
    UserTokens refreshedUserTokens;

    try {
      String accessToken = jwtTokenUtil.getAccessTokenWithoutHeader(tokens.accessToken);
      String refreshToken = tokens.refreshToken;
      String username = jwtTokenUtil.getUsernameFromToken(accessToken);

      usersRepository.validateUserTokens(username, accessToken, refreshToken);

      refreshedUserTokens = new UserTokens(
        jwtTokenUtil.generateAccessToken(username),
        jwtTokenUtil.generateRefreshToken()
      );

      usersRepository.updateUserTokens(username, refreshedUserTokens.accessToken, refreshedUserTokens.refreshToken);
    } catch (SignatureException e) {
      throw new AccountsException(Messages.ERROR.INVALID_TOKEN.message);
    }

    return refreshedUserTokens;
  }

}
