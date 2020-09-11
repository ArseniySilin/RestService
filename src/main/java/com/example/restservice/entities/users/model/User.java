package com.example.restservice.entities.users.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
  @Column(name = "id")
  @Getter
  private Integer id;

  @Id
  @Column(name = "key")
  @Getter
  private String key;

  @Column(name = "login")
  @Getter
  private String userName;

  @Column(name = "password")
  @Getter
  private String password;

  @Column(name = "first_name")
  @Getter
  private String firstName = "Foo";

  @Column(name = "last_name")
  @Getter
  private String lastName = "Bar";

  @Column(name = "access_token")
  @Getter
  private String accessToken;

  @Column(name = "refresh_token")
  @Getter
  private String refreshToken;

  @Column(name ="created_on")
  @Getter
  private LocalDateTime createdOn;

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
}
