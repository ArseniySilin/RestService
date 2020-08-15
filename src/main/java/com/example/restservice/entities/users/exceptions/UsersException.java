package com.example.restservice.entities.users.exceptions;

public class UsersException extends RuntimeException {
  public UsersException(Exception e) {
    super(e.getMessage());
  }

  public UsersException(String message) {
    super(message);
  }
}

