package com.example.restservice;

public class User {
  private final String id;
  private final String username;
  private final String password;

  User(String id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public String getId() { return this.id; }
}
