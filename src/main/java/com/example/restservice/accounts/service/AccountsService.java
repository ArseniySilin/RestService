package com.example.restservice.accounts.service;

import com.example.restservice.Messages;
import com.example.restservice.accounts.exceptions.AccountsException;
import com.example.restservice.accounts.model.User;
import com.example.restservice.accounts.repository.UsersRepository;
import com.example.restservice.execptions.EntityAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

  @Autowired
  UsersRepository usersRepository;

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
}
