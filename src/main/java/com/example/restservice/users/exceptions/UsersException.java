package com.example.restservice.users.exceptions;

public class UsersException extends RuntimeException {
  public UsersException(Exception e) {
    super(e.getMessage());
  }

  public UsersException(String message) {
    super(message);
  }
}

