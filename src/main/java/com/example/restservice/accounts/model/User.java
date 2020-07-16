package com.example.restservice.accounts.model;

public class User {
  private final String id;
  private final String key;
  private final String username;
  private final String password;
  private final String firstName;
  private final String lastName;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.key = null;
    this.firstName = null;
    this.lastName = null;
    this.id = null;
  }

  public User(String id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.key = null;
    this.firstName = null;
    this.lastName = null;
  }

  public User(String id, String key, String username, String password, String firstName, String lastName) {
    this.id = id;
    this.key = key;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public User(User userWithoutKey, String key) {
    this.id = userWithoutKey.getId();
    this.key = key;
    this.username = userWithoutKey.getUsername();
    this.password = userWithoutKey.getPassword();
    this.firstName = userWithoutKey.getFirstName();
    this.lastName = userWithoutKey.getLastName();
  }

  public String getUsername() {
    return this.username;
  }
  public String getPassword() {
    return this.password;
  }
  public String getId() { return this.id; }
  public String getKey() { return this.key; }
  public String getFirstName() { return this.firstName; }
  public String getLastName() { return this.lastName; }
}
