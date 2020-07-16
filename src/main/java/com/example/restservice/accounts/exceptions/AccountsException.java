package com.example.restservice.accounts.exceptions;

public class AccountsException extends RuntimeException {
  public AccountsException(Exception e) {
    super(e.getMessage());
  }

  public AccountsException(String message) {
    super(message);
  }
}

