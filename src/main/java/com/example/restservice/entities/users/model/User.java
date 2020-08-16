package com.example.restservice.entities.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User implements Serializable {
  @Column(name = "id")
  private Integer id;

  @Id
  @Column(name = "key")
  private String key;

  @Column(name = "login")
  private String userName;

  @Column(name = "password")
  private String password;

  @Column(name = "first_name")
  private String firstName = "Foo";

  @Column(name = "last_name")
  private String lastName = "Bar";

  @Column(name = "access_token")
  private String accessToken;

  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name ="created_on")
  private LocalDateTime createdOn;

  public User() {}

  public User(String userName, String password) {
    this.userName = userName;
    this.password = password;
    this.key = null;
    this.firstName = null;
    this.lastName = null;
  }

  public User(Integer id, String userName, String password) {
    this.id = id;
    this.userName = userName;
    this.password = password;
    this.key = null;
    this.firstName = null;
    this.lastName = null;
  }

  public User(Integer id, String key, String userName, String password, String firstName, String lastName) {
    this.id = id;
    this.key = key;
    this.userName = userName;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public User(
    String key,
    String userName,
    String password,
    String firstName,
    String lastName,
    String accessToken,
    String refreshToken
  ) {
    this.key = key;
    this.userName = userName;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public User(
    String key,
    String userName,
    String password,
    LocalDateTime createdOn
  ) {
    this.key = key;
    this.userName = userName;
    this.password = password;
    this.createdOn = createdOn;
  }

  public User(User userWithoutKey, String key) {
    this.key = key;
    this.userName = userWithoutKey.getUserName();
    this.password = userWithoutKey.getPassword();
    this.firstName = userWithoutKey.getFirstName();
    this.lastName = userWithoutKey.getLastName();
  }

  public String getUserName() {
    return this.userName;
  }
  public String getPassword() {
    return this.password;
  }
  public Integer getId() { return this.id; }
  public String getKey() { return this.key; }
  public String getFirstName() { return this.firstName; }
  public String getLastName() { return this.lastName; }

  public String getAccessToken() {
    return accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }
}
