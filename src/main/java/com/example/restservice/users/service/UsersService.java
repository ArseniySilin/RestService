package com.example.restservice.users.service;

import com.example.restservice.JwtTokenUtil;
import com.example.restservice.Messages;
import com.example.restservice.UserTokens;
import com.example.restservice.users.exceptions.UsersException;
import com.example.restservice.users.model.Account;
import com.example.restservice.users.model.User;
import com.example.restservice.users.repository.UsersRepository;
import com.example.restservice.execptions.EntityAlreadyExistsException;
import com.example.restservice.workgroups.service.WorkgroupsService;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

  @Autowired
  UsersRepository usersRepository;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  WorkgroupsService workgroupsService;

  private boolean getPasswordSafety(String password) {
    return hasMinimumSafetyLength(password);
  }

  boolean hasMinimumSafetyLength(String str) {
    return str != null && str.length() >= 6;
  }

  public void addUser(User user) throws EntityAlreadyExistsException, UsersException {
    if (usersRepository.getUser(user.getUsername()) != null) {
      throw new EntityAlreadyExistsException(User.class);
    }

    String username = user.getUsername();
    String password = user.getPassword();

    if (!hasMinimumSafetyLength(username)) {
      throw new UsersException(Messages.ERROR.UNSAFE_USERNAME.message);
    }

    boolean isPasswordSafe = getPasswordSafety(password);
    if (!isPasswordSafe) {
      throw new UsersException(Messages.ERROR.UNSAFE_PASSWORD.message);
    }

    usersRepository.addUser(user);
  }

  public UserTokens getTokens(Account account) throws UsersException {
    usersRepository.authorizeUser(account.getUsername(), account.getPassword());

    UserTokens userTokens = usersRepository.generateUserTokens(account.getUsername());

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

      usersRepository.validateUserTokens(username, accessToken, refreshToken);

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

  public User getAuthorizedUser(String bearerToken, String workGroupKey) {
    System.out.println("getAuthorizedUser::bearerToken: " + bearerToken);

    String userName = jwtTokenUtil.getUsernameFromBearerToken(bearerToken);

    System.out.println("getAuthorizedUser::userName: " + userName);
    User user = usersRepository.getUser(userName);

    boolean isWorkGroupIncludesUser = workgroupsService.isWorkGroupIncludesUser(workGroupKey, user);

    if (!isWorkGroupIncludesUser) return null;

    return user;
  }

}