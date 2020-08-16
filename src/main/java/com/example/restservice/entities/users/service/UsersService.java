package com.example.restservice.entities.users.service;

import com.example.restservice.Messages;
import com.example.restservice.entities.users.exceptions.UsersException;
import com.example.restservice.entities.users.model.Account;
import com.example.restservice.entities.users.model.User;
import com.example.restservice.entities.users.model.UserTokens;
import com.example.restservice.entities.users.repository.UsersRepository;
import com.example.restservice.execptions.EntityAlreadyExistsException;
import com.example.restservice.execptions.EntityNotFoundException;
import com.example.restservice.utils.HashString;
import com.example.restservice.utils.jwt.JwtTokenUtil;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersService {
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

  public User getUser(String username) throws EntityNotFoundException {
    User user = usersRepository.findByUserName(username);

    if (user == null) throw new EntityNotFoundException(User.class);

    return user;
  }

  public UserTokens generateUserTokens(String username) throws EntityNotFoundException {
    User user = getUser(username);

    String accessToken = jwtTokenUtil.generateAccessToken(username, user.getId(), user.getKey());
    String refreshToken  = jwtTokenUtil.generateRefreshToken();
    UserTokens userToken = new UserTokens(accessToken, refreshToken);

    return userToken;
  }

  public boolean validateUserTokens(String username, String accessToken, String refreshToken)
    throws EntityNotFoundException {
    User user = getUser(username);

    return user.getAccessToken().equals(accessToken) && user.getRefreshToken().equals(refreshToken);
  }

  public org.springframework.security.core.userdetails.User getUserByUserName(String username)
    throws EntityNotFoundException {
    User user = getUser(username);
    List authorities = new ArrayList<>();

    return new org.springframework.security.core.userdetails.User(
      username,
      user.getPassword(),
      authorities
    );
  }

//  public void addUser(User user) throws EntityAlreadyExistsException {
//    if (getUser(user.getUserName()) != null) throw new EntityAlreadyExistsException(User.class);
//
//    usersRepository.save(user);
//  }

  public void addUser(String userName, String password) throws EntityAlreadyExistsException, UsersException {
    if (usersRepository.findByUserName(userName) != null) {
      throw new EntityAlreadyExistsException(User.class);
    }

    if (!hasMinimumSafetyLength(userName)) {
      throw new UsersException(Messages.ERROR.UNSAFE_USERNAME.message);
    }

    boolean isPasswordSafe = getPasswordSafety(password);
    if (!isPasswordSafe) {
      throw new UsersException(Messages.ERROR.UNSAFE_PASSWORD.message);
    }

    String key = UUID.randomUUID().toString();
    User user = new User(
      key,
      userName,
      new HashString().getHash(password),
      LocalDateTime.now()
    );

    usersRepository.save(user);
  }

  public void authorizeUser(String login, String password) throws UsersException, EntityNotFoundException {
    User user = usersRepository.findByUserName(login);
    if (user == null) throw new EntityNotFoundException(User.class);

    boolean isPasswordMatches = new HashString().isMatches(user.getPassword(), password);
    if (!isPasswordMatches) throw new UsersException(Messages.ERROR.INCORRECT_PASSWORD.message);
  }

  public UserTokens getTokens(Account account) throws UsersException, EntityNotFoundException {
    authorizeUser(account.getUsername(), account.getPassword());

    UserTokens userTokens = generateUserTokens(account.getUsername());

    usersRepository.updateUserTokens(
      account.getUsername(),
      userTokens.accessToken,
      userTokens.refreshToken
    );

    return userTokens;
  }

  public UserTokens refreshTokens(UserTokens tokens) throws UsersException {
    UserTokens refreshedUserTokens;

    try {
      String accessToken = jwtTokenUtil.getAccessTokenWithoutHeader(tokens.accessToken);
      String refreshToken = tokens.refreshToken;
      String username = jwtTokenUtil.getUsernameFromToken(accessToken);

      validateUserTokens(username, accessToken, refreshToken);

      refreshedUserTokens = new UserTokens(
        jwtTokenUtil.generateAccessToken(username),
        jwtTokenUtil.generateRefreshToken()
      );

      usersRepository.updateUserTokens(username, refreshedUserTokens.accessToken, refreshedUserTokens.refreshToken);
    } catch (SignatureException e) {
      throw new UsersException(Messages.ERROR.INVALID_TOKEN.message);
    }

    return refreshedUserTokens;
  }
}
